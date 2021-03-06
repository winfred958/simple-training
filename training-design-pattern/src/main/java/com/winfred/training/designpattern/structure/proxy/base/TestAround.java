package com.winfred.training.designpattern.structure.proxy.base;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestAround {
  String value() default "";
}
