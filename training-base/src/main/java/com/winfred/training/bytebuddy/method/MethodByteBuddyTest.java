package com.winfred.training.bytebuddy.method;

import com.alibaba.fastjson.JSON;
import com.winfred.training.bytebuddy.base.TestService;
import com.winfred.training.reflect.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodCall;

import java.util.concurrent.Callable;

/**
 * @author winfred958
 */
@Slf4j
public class MethodByteBuddyTest {

    private static final String methodName = "getKey";

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        final TestService testService = new ByteBuddy()
            .subclass(TestService.class)
            .defineMethod(methodName, String.class)
            .intercept(MethodCall.call(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "test";
                }
            }))
            .make()
            .load(ClassLoader.getSystemClassLoader())
            .getLoaded()
            .newInstance();

        final Object invoke = ReflectUtils.invoke(testService, methodName);
        log.info("{}", JSON.toJSONString(invoke));
    }
}
