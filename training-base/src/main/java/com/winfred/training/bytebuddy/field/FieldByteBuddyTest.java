package com.winfred.training.bytebuddy.field;

import com.alibaba.fastjson.JSON;
import com.winfred.training.bytebuddy.base.TestService;
import com.winfred.training.reflect.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;

/**
 * @author winfred958
 */
@Slf4j
public class FieldByteBuddyTest {

    private static final String fieldName = "key";

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        final TestService testService = new ByteBuddy()
            .subclass(TestService.class)
            .defineField(fieldName, String.class)
            .make()
            .load(ClassLoader.getSystemClassLoader())
            .getLoaded()
            .newInstance();

        final Object value = ReflectUtils.getFieldValue(testService, fieldName);
        log.info("{}", JSON.toJSONString(value));
    }
}
