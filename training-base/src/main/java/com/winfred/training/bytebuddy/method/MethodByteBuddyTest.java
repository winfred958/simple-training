package com.winfred.training.bytebuddy.method;

import com.alibaba.fastjson.JSON;
import com.winfred.training.bytebuddy.base.TestService;
import com.winfred.training.bytebuddy.base.TheKey;
import com.winfred.training.reflect.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.UUID;

/**
 * 字节码增强
 * 新增 Field: key
 * 新增 Method: getKey
 *
 * @author winfred958
 */
@Slf4j
public class MethodByteBuddyTest {

  private static final String fieldName = "key";
  private static final String methodName = "getKey";

  public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
    final TestService testService = new ByteBuddy()
        .subclass(TestService.class)
        .defineField(fieldName, String.class, Modifier.PRIVATE)
        .defineMethod(methodName, String.class, Modifier.PUBLIC)
        .intercept(
            MethodDelegation.to(KeyDelegation.class)
        )
        .make()
        .load(ClassLoader.getSystemClassLoader())
        .getLoaded()
        .newInstance();
    String uuid = UUID.randomUUID().toString();
    testService.setUuid(uuid);

    final Object key = ReflectUtils.invoke(testService, methodName);
    log.info("{}", JSON.toJSONString(testService));
  }

  public static class KeyDelegation {

    /**
     * （MethodDelegation）的方式实现拦截器，
     * {@link @This} 表示生成的代理对象
     * {@link @Origin} 表示被代理的方法
     * {@link @AllArguments} 表示方法参数
     *
     * @param obj
     * @param method
     * @return
     * @throws CloneNotSupportedException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @RuntimeType
    public static String getKey(@This Object obj, @Origin Method method) throws CloneNotSupportedException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
      Object result = null;
      TheKey annotation = obj.getClass().getAnnotation(TheKey.class);
      if (annotation != null) {
        String key = annotation.key();
        // 反射获取不到父类字段, 所以调用get方法
        String methodName = "get" + StringUtils.capitalize(key);
        result = ReflectUtils.invoke(obj, methodName);
        ReflectUtils.setFieldValue(obj, fieldName, result);
      }
      return String.valueOf(result);
    }
  }
}
