package com.cl.interview.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 有次注解的方法不会进行登录验证
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface NoneAuth {
}
