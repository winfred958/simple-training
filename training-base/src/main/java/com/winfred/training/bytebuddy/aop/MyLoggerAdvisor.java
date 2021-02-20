package com.winfred.training.bytebuddy.aop;

import com.alibaba.fastjson.JSON;
import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;

/**
 * @author winfred958
 */
public class MyLoggerAdvisor {

  @Advice.OnMethodEnter
  public static void before(
      @Advice.Origin Method method,
      @Advice.AllArguments Object[] arguments
  ) {
    System.out.println(method.getName() + "(" + ((arguments.length == 0) ? "" : JSON.toJSONString(arguments)) + ") enter");
  }

  @Advice.OnMethodExit
  public static void after(
      @Advice.Origin Method method,
      @Advice.AllArguments Object[] arguments
  ) {
    System.out.println(method.getName() + "(" + ((arguments.length == 0) ? "" : JSON.toJSONString(arguments)) + ") exit");
  }
}
