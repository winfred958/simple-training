package com.winfred.traning.designpattern.behavioral.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK代理是不需要以来第三方的库，只要要JDK环境就可以进行代理，它有几个要求
 * * 实现InvocationHandler
 * * 使用Proxy.newProxyInstance产生代理对象
 * * 被代理的对象必须要实现接口
 *
 * @author z
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (null != method.getAnnotation(TestBefore.class)) {
            before();
            result = method.invoke(target, args);
        } else if (null != method.getAnnotation(TestAfter.class)) {
            result = method.invoke(target, args);
            after();
        } else if (null != method.getAnnotation(TestAround.class)){
            before();
            result = method.invoke(target, args);
            after();
        } else {
            before();
            result = method.invoke(target, args);
            after();
        }
        return result;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
    }

    private void before() {
        System.out.println(String.format("%s : %s", this.getClass().getName(), " --- before ---"));
    }

    private void after() {
        System.out.println(String.format("%s : %s", this.getClass().getName(), " --- after ---"));
    }

}
