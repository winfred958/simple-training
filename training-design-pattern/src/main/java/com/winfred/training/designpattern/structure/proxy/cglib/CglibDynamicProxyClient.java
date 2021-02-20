package com.winfred.training.designpattern.structure.proxy.cglib;

import com.winfred.training.designpattern.structure.proxy.base.ProxyEntity;
import com.winfred.training.designpattern.structure.proxy.base.UserService;
import com.winfred.training.designpattern.structure.proxy.base.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class CglibDynamicProxyClient {


  @Test
  public void cglibProxyTest() {
    MyMethodInterceptor cgLibProxy = new MyMethodInterceptor();
    UserService userService = (UserService) cgLibProxy.createProxyObject(new UserServiceImpl());

    ProxyEntity entity = new ProxyEntity();
    entity.setName("xxx");

    userService.testAround(entity);
  }
}
