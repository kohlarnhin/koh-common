package com.koh.common.redis.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koh.common.core.utils.StringUtils;
import com.koh.common.redis.annotation.RepeatSubmit;
import com.koh.common.redis.config.RepeatSubmitConfig;
import com.koh.common.redis.exception.RepeatSubmitException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

/**
 * 防止重复提交切面
 * <p>
 * 作者：kohlarnhin
 * 时间：2023年08月06日
 */
@Aspect
@Component
public class RepeatSubmitAspect {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private Environment env;

    /**
     * 用户 ID 提供者
     */
    private final RequestIdProvider userIdProvider;

    /**
     * 防止重复提交配置
     */
    private final RepeatSubmitConfig repeatSubmitConfig;

    /**
     * 构造函数
     *
     * @param requestIdProvider  用户 ID 提供者
     * @param repeatSubmitConfig 防止重复提交配置
     */
    public RepeatSubmitAspect(RequestIdProvider requestIdProvider, RepeatSubmitConfig repeatSubmitConfig) {
        this.userIdProvider = requestIdProvider;
        this.repeatSubmitConfig = repeatSubmitConfig;
    }

    /**
     * 定义切点，匹配所有使用 @RepeatSubmit 注解的方法
     */
    @Pointcut("@annotation(com.koh.common.redis.annotation.RepeatSubmit)")
    public void repeatSubmitPointcut() {
    }

    /**
     * 环绕通知，用于防止重复提交
     *
     * @param joinPoint    切入点
     * @param repeatSubmit 防止重复提交注解
     * @return 切入点方法的返回值
     * @throws Throwable 抛出异常
     */
    @Around("repeatSubmitPointcut() && @annotation(repeatSubmit)")
    public Object repeatSubmitAround(ProceedingJoinPoint joinPoint, RepeatSubmit repeatSubmit) throws Throwable {
        // 如果未开启重复提交检查，则直接执行被切入的方法
        if (!repeatSubmitConfig.isEnable()) {
            return joinPoint.proceed();
        }

        // 获取请求信息
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        // 获取服务名称
        String serviceName = repeatSubmitConfig.getServiceName();
        if (StringUtils.isBlank(serviceName)) {
            // 如果未配置服务名称，则获取当前服务的名称
            serviceName = env.getProperty("spring.application.name");
        }

        // 生成 Redis key
        String requestId = userIdProvider.getRequestId(request);
        String requestUrl = request.getRequestURI();
        Object requestBody = null;
        // 如果是 POST 或 PUT 请求，则获取请求体
        if (HttpMethod.POST.name().equals(request.getMethod())
                || HttpMethod.PUT.name().equals(request.getMethod())
        ) {
            requestBody = getRequestBody(request, joinPoint.getArgs());
        }
        String data = requestId + ":" + requestUrl + requestBody;
        String key = serviceName + ":" + DigestUtils.md5Hex(data);
        //校验数据
        checkNeedParam(requestId, serviceName);
        // 判断 Redis key 是否存在
        String value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            throw new RepeatSubmitException(String.format("请勿重复提交请求,请求服务:%s,请求唯一标识:%s,请求地址:%s", serviceName, requestId, requestUrl), serviceName, key, requestUrl, String.valueOf(requestBody));
        }

        // 将 Redis key 存入 Redis 中，并设置过期时间和单位
        redisTemplate.opsForValue().set(key, "1", repeatSubmit.interval(), repeatSubmit.timeUnit());

        // 执行原始方法
        return joinPoint.proceed();
    }

    /**
     * 校验必要参数
     *
     * @param requestId  请求唯一标识
     * @param serviceName 服务名称
     */
    private void checkNeedParam(String requestId, String serviceName) {
        if (StringUtils.isBlank(requestId)) {
            throw new RepeatSubmitException(String.format("请实现RequestIdProvider 接口，并返回唯一请求标识"), serviceName, null, null, null);
        }
    }

    /**
     * 获取请求体
     *
     * @param request 请求
     * @param args    参数
     * @return 请求体
     * @throws IOException 抛出异常
     */
    private Object getRequestBody(HttpServletRequest request, Object[] args) throws IOException {
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                continue;
            }
            try {
                // 尝试将参数转换为 Java 对象
                return objectMapper.readValue(objectMapper.writeValueAsString(arg), Object.class);
            } catch (Exception e) {
                // 如果转换失败，则继续尝试下一个参数
                continue;
            }
        }
        // 如果所有参数都不是 Java 对象，则读取请求体的内容并解析为 Java 对象
        String requestBody = getRequestBodyAsString(request);
        return objectMapper.readValue(requestBody, Object.class);
    }

    /**
     * 获取请求体
     *
     * @param request 请求
     * @return 请求体
     * @throws IOException 抛出异常
     */
    private String getRequestBodyAsString(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}