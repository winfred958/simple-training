package com.winfred.training.bytebuddy.base;

import java.lang.annotation.*;

/**
 * @author winfred958
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface TheKey {
  String key() default "key";
}
