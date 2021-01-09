package com.winfred.training.reflect;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author winfred958
 */
@Slf4j
public class ReflectUtils {

    public static <T> void setFieldValue(T obj, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        final Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static <T> Object getFieldValue(T obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    public static <T, V> V invoke(T obj, String methodName, Object... parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = obj.getClass().getMethod(methodName);
        return (V) method.invoke(obj, parameters);
    }

    public static Map<String, Method> getMethodMap(Class<?> clazz) {
        Map<String, Method> result = new HashMap<>(16);
        Arrays.stream(clazz
            .getMethods())
            .distinct()
            .forEach(method -> {
                result.put(method.getName(), method);
            });
        return result;
    }

    public static Map<String, Field> getFieldMap(Class<?> clazz) {
        Map<String, Field> result = new HashMap<>(16);
        Arrays.stream(clazz.getFields())
            .distinct()
            .forEach(field -> {
                result.put(field.getName(), field);
            });
        return result;
    }
}
