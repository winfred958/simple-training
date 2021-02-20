package com.winfred.training.neo4j.remote.ogm.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ReflactorUtil {

  public static Field getAnnotationField(Object obj, Class<? extends Annotation> clazz) {
    Field[] fields = obj.getClass().getDeclaredFields();
    for (int i = 0, len = fields.length; i < len; i++) {
      Field field = fields[i];
      Annotation annotation = field.getAnnotation(clazz);
      if (null != annotation) {
        return field;
      }
    }
    return null;
  }
}
