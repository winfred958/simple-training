package com.winfred.training.bytebuddy.method;

import com.alibaba.fastjson.JSON;
import com.winfred.training.bytebuddy.base.TestService;
import com.winfred.training.reflect.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
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
        final String uuid1 = testService.getUuid();
        final Object key = ReflectUtils.invoke(testService, "getKey");
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
            final Object uuid = ReflectUtils.invoke(obj, "getUuid");
            ReflectUtils.setFieldValue(obj, fieldName, uuid);
            return String.valueOf(uuid);
        }
    }
}
