package com.lxinet.jeesns.core.annotation;

import com.lxinet.jeesns.interceptor.JeesnsInterceptor;

import java.lang.annotation.*;


@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface After {
    Class<? extends JeesnsInterceptor> value();
}
