package com.winfred.training.bytebuddy.aop;

import com.winfred.training.bytebuddy.base.TestService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

/**
 * @author winfred958
 */
@Slf4j
public class ByteBuddyAopTest {

  private ClassLoader getCurrentClassLoader() {
    return Thread.currentThread().getContextClassLoader();
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
