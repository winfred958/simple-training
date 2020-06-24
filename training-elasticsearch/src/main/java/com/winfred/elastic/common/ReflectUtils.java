package com.winfred.elastic.common;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Slf4j
public class ReflectUtils {
  /**
   * 获取指定注解字段的值
   *
   * @param obj
   * @param clazz
   * @return
   */
  public static String getAnnotationFiledValue(Object obj, Class<? extends Annotation> clazz) {
    Object value = null;
    Field[] declaredFields = obj.getClass().getDeclaredFields();
    for (Field field : declaredFields) {
      Annotation annotation = field.getDeclaredAnnotation(clazz);
      if (null != annotation) {
        field.setAccessible(true);
        try {
          value = field.get(obj);
        } catch (IllegalAccessException e) {
          log.error("reflect failed", e);
        }
        break;
      }
    }
    return String.valueOf(value);
  }
}
