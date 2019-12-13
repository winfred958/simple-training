package com.winfred.training.designpattern.structure.proxy.jdk;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestAround {
    String value() default "";
}
