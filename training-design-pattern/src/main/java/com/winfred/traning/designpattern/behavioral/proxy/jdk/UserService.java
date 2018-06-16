package com.winfred.traning.designpattern.behavioral.proxy.jdk;

public interface UserService {

    @TestBefore
    void testBefore();

    @TestAfter
    void testAfter();

    @TestAround
    void testAround();
}
