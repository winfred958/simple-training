package com.winfred.training.designpattern.structure.proxy.cglib;

import com.winfred.training.designpattern.structure.proxy.base.UserService;
import com.winfred.training.designpattern.structure.proxy.base.UserServiceImpl;
import com.winfred.training.designpattern.structure.proxy.jdk.MyInvocationHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.misc.ProxyGenerator;
import sun.nio.fs.WindowsFileSystemProvider;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@Slf4j
public class CglibDynamicProxyClient {


    @Test
    public void cglibProxyTest() {

        MyMethodInterceptor cgLibProxy = new MyMethodInterceptor();
        UserService userService = (UserService) cgLibProxy.createProxyObject(new UserServiceImpl());

        userService.testAround();
    }
}
