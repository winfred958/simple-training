package com.winfred.training.designpattern.structure.proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib 动态代理
 * <p>
 * 字节码增强
 */
@Slf4j
public class MyMethodInterceptor implements MethodInterceptor {
  
  private Object target;
  
  public Object createProxyObject(Object target) {
    this.target = target;
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(target.getClass());
    enhancer.setCallback(this);
    Object proxyObj = enhancer.create();
    return proxyObj;// 返回增强的代理对象
  }
  
  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
    Object result;
    
    log.info("o: {} \ntarget:{} \nmethod: {} \nsignature: {}", o.getClass().getName(), target.getClass().getName(), method.getName(), methodProxy.getSignature().getName());
    before();
    result = method.invoke(target, objects);
    after();
    
    return result;
  }
  
  private void before() {
    log.info("{} {}", this.getClass().getName(), " --- before ---");
  }
  
  private void after() {
    log.info("{} {}", this.getClass().getName(), " --- after ---");
  }
  
}
