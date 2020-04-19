package com.winfred.training.designpattern.structure.proxy.base;

public interface UserService {

    @TestBefore
    void testBefore(ProxyEntity entity);

    @TestAfter
    void testAfter(ProxyEntity entity);

    @TestAround
    void testAround(ProxyEntity entity);
}
