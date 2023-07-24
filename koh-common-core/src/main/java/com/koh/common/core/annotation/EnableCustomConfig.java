package com.koh.common.core.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.annotation.*;

/**
 * @author 一杯清水
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
@Documented
@ComponentScan(basePackages = "com.koh")
public @interface EnableCustomConfig {

}
