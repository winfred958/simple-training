package com.winfred.training.bytebuddy.field;

import com.alibaba.fastjson.JSON;
import com.winfred.training.bytebuddy.base.TestService;
import com.winfred.training.reflect.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * @author winfred958
 */
@Slf4j
public class FieldByteBuddyTest {

  private static final String FIELD_NAME = "key";

  public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
    final TestService testService = new ByteBuddy()
        .subclass(TestService.class)
        .defineField(FIELD_NAME, String.class, Modifier.PRIVATE)
        .make()
        .load(ClassLoader.getSystemClassLoader())
        .getLoaded()
        .newInstance();

    ReflectUtils.setFieldValue(testService, FIELD_NAME, testService.getUuid());
    final Object value = ReflectUtils.getFieldValue(testService, FIELD_NAME);
    log.info("{} = {}", FIELD_NAME, value);
    log.info("{}", JSON.toJSONString(testService));
  }
}