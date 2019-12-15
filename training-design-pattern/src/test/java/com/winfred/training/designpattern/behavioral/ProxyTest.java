package com.winfred.training.designpattern.behavioral;

import com.winfred.training.designpattern.structure.proxy.cglib.CGLibProxy;
import com.winfred.training.designpattern.structure.proxy.cglib.UserService;
import com.winfred.training.designpattern.structure.proxy.cglib.UserServiceImpl;
import com.winfred.training.designpattern.structure.proxy.jdk.MyInvocationHandler;
import org.junit.Test;


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

        CGLibProxy cgLibProxy = new CGLibProxy();
        UserService userService = (UserService) cgLibProxy.createProxyObject(new UserServiceImpl());

        userService.testAround();
    }
}
