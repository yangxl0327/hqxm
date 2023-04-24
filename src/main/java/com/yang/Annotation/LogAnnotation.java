package com.yang.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//何时生效
@Retention(RetentionPolicy.RUNTIME)
//作用在何处  方法上
@Target(ElementType.METHOD)
public @interface LogAnnotation {
    String value();
}
