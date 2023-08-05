package com.koh.common.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 重复提交注解，用于防止用户重复提交表单等操作。
 * <p>
 * 作者：kohlarnhin
 * 时间：2023年08月06日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmit {
    /**
     * 两次请求之间的时间间隔，也是 Redis 键的过期时间。默认为 1000 毫秒。
     */
    long interval() default 1000;

    /**
     * Redis 键的过期时间的时间单位。默认为毫秒。
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}