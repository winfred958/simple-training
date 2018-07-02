package com.winfred.training.designpattern.behavioral.proxy.jdk;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestBefore {
    String value() default "";
}
