package com.winfred.training.designpattern.structure;

import com.winfred.training.designpattern.structure.proxy.base.UserService;
import com.winfred.training.designpattern.structure.proxy.base.UserServiceImpl;
import com.winfred.training.designpattern.structure.proxy.cglib.MyMethodInterceptor;
import com.winfred.training.designpattern.structure.proxy.jdk.MyInvocationHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ProxyTest {

    @Test
    public void jdkProxyTest() {
        // jdk proxy 只支持被代理的对象必须要实现接口
        UserService userService = new UserServiceImpl();
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(userService);

        UserService proxy = (UserService) myInvocationHandler.getProxy();
        proxy.testBefore();
        System.out.println("=====================");
        proxy.testAfter();
        System.out.println("=====================");
        proxy.testAround();
        System.out.println("=====================");
    }

    @Test
    public void cglibProxyTest() {

        MyMethodInterceptor cgLibProxy = new MyMethodInterceptor();
        UserService userService = (UserService) cgLibProxy.createProxyObject(new UserServiceImpl());

        userService.testAround();
    }
}
