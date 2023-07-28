package com.koh.common.core.aspect;

import com.alibaba.fastjson.JSON;
import com.koh.common.core.constant.BizCodeEnum;
import com.koh.common.core.utils.R;
import com.koh.common.core.utils.WebUtils;
import com.koh.common.core.valid.RequireValid;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kohlarnhin
 * @create 2022/3/17 18:59
 */
@Slf4j
@Aspect
@Component
public class ValidAspect {


    /**
     * 构建
     */
    public ValidAspect() {

    }

    /**
     * 定义AOP签名 (切入所有使用鉴权注解的方法)
     */
    public static final String POINTCUT_SIGN = " @annotation(com.cfc.common.core.valid.RequireValid)";

    /**
     * 声明AOP签名
     */
    @Pointcut(POINTCUT_SIGN)
    public void pointcut() {

    }

    /**
     * 环绕切入
     *
     * @param joinPoint 切面对象
     * @return 底层方法执行后的返回值
     * @throws Throwable 底层方法抛出的异常
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("进入aop");
        // 获取所有的请求参数
        Object[] args = joinPoint.getArgs();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        if (null != args && args.length > 0) {
            for (Object obj : args) {
                if (obj instanceof BindingResult) {
                    if (((BindingResult) obj).hasErrors()) {
                        Map<String, String> map = new HashMap<>();
                        // 参数验证
                        ((BindingResult) obj).getFieldErrors().forEach(info -> {
                            String message = info.getDefaultMessage();
                            String field = info.getField();
                            map.put(field, message);
                        });
                        R<Map<String, String>> r = R.fail(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg(),map);
                        WebUtils.renderString(response, JSON.toJSONString(r));
                        return null;
                    }
                }
            }
        }
        return joinPoint.proceed(args);
    }

    @AfterThrowing(value = "@annotation(requireValid)", throwing = "e")
    public void AfterThrowing(JoinPoint joinPoint, RequireValid requireValid, Exception e){
        log.info("拦截异常");
    }
}
