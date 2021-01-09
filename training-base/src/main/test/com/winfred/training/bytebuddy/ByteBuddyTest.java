package com.winfred.training.bytebuddy;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
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
                .load(this.getClass().getClassLoader())
                .getLoaded()
                .newInstance();
            log.info(obj.toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
