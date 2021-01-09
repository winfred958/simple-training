package com.winfred.training.bytebuddy.aop;

import java.lang.annotation.*;

/**
 * @author winfred958
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface MyLogger {
}
