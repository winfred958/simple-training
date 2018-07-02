package com.winfred.training.designpattern.behavioral.proxy.cglib;

import com.winfred.training.designpattern.behavioral.proxy.jdk.TestAfter;
import com.winfred.training.designpattern.behavioral.proxy.jdk.TestAround;
import com.winfred.training.designpattern.behavioral.proxy.jdk.TestBefore;

public interface UserService {

    @TestBefore
    void testBefore();

    @TestAfter
    void testAfter();

    @TestAround
    void testAround();
}
