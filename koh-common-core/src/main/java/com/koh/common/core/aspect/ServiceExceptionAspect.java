package com.koh.common.core.aspect;

import com.koh.common.core.exception.BusinessException;
import com.koh.common.core.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author kohlarnhin
 * @create 2022/7/15 14:46
 */
@Aspect
@Component
@Slf4j
public class ServiceExceptionAspect {

    /**
     * 构建
     */
    public ServiceExceptionAspect() {

    }

    /**
     * 定义AOP签名 (切入所有使用鉴权注解的方法)
     */
    public static final String POINTCUT_SIGN = " @annotation(com.cfc.common.core.annotation.ServiceExceptionHandler)";

    /**
     * 声明AOP签名
     */
    @Pointcut(POINTCUT_SIGN)
    public void exceptionHandler() {

    }

    /**
     * 当有ServiceExceptionHandler的注解的方法抛出异常的时候，做如下的处理
     */
    @AfterThrowing(pointcut = "within(com.koh.*.service.impl..*)", throwing = "e")
    public void afterThrowable(JoinPoint joinPoint, Exception e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String ip = IpUtils.getIpAddr(attributes.getRequest());
        log.error("\n异常方法名{}\n用户IP:{}", joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName(), ip);
        log.error("异常信息",e);
        throw new BusinessException("请求异常,请重试");
    }


}
