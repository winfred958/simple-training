package com.winfred.training.bytebuddy.aop;

import com.winfred.training.bytebuddy.base.TestService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * Byte-Buddy 实现 AOP (Aspect-Oriented Programming)
 *
 * @author winfred958
 */
@Slf4j
public class TestClient {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        final TestService testService = new ByteBuddy()
            .subclass(TestService.class)
            // 匹配指定注解的方法
            .method(ElementMatchers.isAnnotatedWith(MyLogger.class))
            // 拦截方法实现类
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

    private static ClassLoader getCurrentClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
