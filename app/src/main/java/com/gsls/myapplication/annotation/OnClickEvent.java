package com.gsls.myapplication.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClickEvent {
    Class<?> listenerType();//接口类型
    String listenerSetter();//设置的方法
    String methodName();//接口里面要实现的方法
}

