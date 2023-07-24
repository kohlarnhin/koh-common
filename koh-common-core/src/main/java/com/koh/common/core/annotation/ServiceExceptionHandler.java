package com.koh.common.core.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.annotation.*;

/**
 * @author kohlarnhin
 * @create 2022/7/15 15:00
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
@Documented
@ComponentScan(basePackages = "com.koh")
public @interface ServiceExceptionHandler {

}
