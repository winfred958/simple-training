package com.winfred.traning.designpattern.behavioral.proxy.cglib;

import com.winfred.traning.designpattern.behavioral.proxy.jdk.TestAfter;
import com.winfred.traning.designpattern.behavioral.proxy.jdk.TestAround;
import com.winfred.traning.designpattern.behavioral.proxy.jdk.TestBefore;

public interface UserService {

    @TestBefore
    void testBefore();

    @TestAfter
    void testAfter();

    @TestAround
    void testAround();
}
