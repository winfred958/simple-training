package com.winfred.traning.designpattern.behavioral.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibProxy implements MethodInterceptor {

    private Object target;

    public Object createProxyObject(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        Object proxyObj = enhancer.create();
        return proxyObj;// 返回代理对象
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result;

        before();
        result = method.invoke(target, objects);
        after();

        return result;
    }

    private void before() {
        System.out.println(String.format("%s : %s", this.getClass().getName(), " --- before ---"));
    }

    private void after() {
        System.out.println(String.format("%s : %s", this.getClass().getName(), " --- after ---"));
    }


}
