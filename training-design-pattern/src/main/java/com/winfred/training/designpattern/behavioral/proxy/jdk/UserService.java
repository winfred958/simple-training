package com.winfred.training.designpattern.behavioral.proxy.jdk;

public interface UserService {

    @TestBefore
    void testBefore();

    @TestAfter
    void testAfter();

    @TestAround
    void testAround();
}
