package com.winfred.training.aop;

import com.winfred.training.bytebuddy.aop.MyLoggerAdvisor;
import com.winfred.training.bytebuddy.aop.TestService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author winfred958
 */
@Slf4j
public class ByteBuddyTest {

    private ClassLoader getCurrentClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    @Test
    public void simpleTest() {
        final DynamicType.Unloaded<Object> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.isToString())
            .intercept(FixedValue.value("Hello Word!"))
            .make();

        try {
            dynamicType.toJar(new File("/tmp.jar"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            final Object obj = dynamicType
                .load(getCurrentClassLoader())
                .getLoaded()
                .newInstance();
            log.info(obj.toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void defineMethodTest() throws IllegalAccessException, InstantiationException {
        final TestService testService = new ByteBuddy()
            .subclass(TestService.class)
            .method(ElementMatchers.any())
            .intercept(Advice.to(MyLoggerAdvisor.class))
            .make()
            .load(getCurrentClassLoader())
            .getLoaded()
            .newInstance();

        testService.setName("xxx");
        testService.getUuid();
        final String name = testService.getName();
        log.info(name);
    }
}
