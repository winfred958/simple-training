package com.winfred.training.designpattern.structure.proxy.jdk;

public interface UserService {

    @TestBefore
    void testBefore();

    @TestAfter
    void testAfter();

    @TestAround
    void testAround();
}
