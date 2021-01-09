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

    public static <T> Object getFieldValue(T obj, String fieldName) {
        final Field field = getFieldMap(obj.getClass()).get(fieldName);
        if (field == null) {
            return null;
        }
        field.setAccessible(true);
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T, V> V invoke(T obj, String methodName, Object... args) {
        final Method method = getMethodMap(obj.getClass()).get(methodName);
        if (method == null) {
            return null;
        }
        try {
            final V invoke = (V) method.invoke(obj, args);
            return invoke;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Method> getMethodMap(Class<?> clazz) {
        Map<String, Method> result = new HashMap<>(16);
        Arrays.stream(clazz
            .getDeclaredMethods())
            .distinct()
            .forEach(method -> {
                result.put(method.getName(), method);
            });
        return result;
    }

    public static Map<String, Field> getFieldMap(Class<?> clazz) {
        Map<String, Field> result = new HashMap<>(16);
        Arrays.stream(clazz.getDeclaredFields())
            .distinct()
            .forEach(field -> {
                result.put(field.getName(), field);
            });
        return result;
    }
}
