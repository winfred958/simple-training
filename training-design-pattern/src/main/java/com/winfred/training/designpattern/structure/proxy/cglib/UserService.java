package com.winfred.training.designpattern.structure.proxy.cglib;

import com.winfred.training.designpattern.structure.proxy.jdk.TestAfter;
import com.winfred.training.designpattern.structure.proxy.jdk.TestAround;
import com.winfred.training.designpattern.structure.proxy.jdk.TestBefore;

public interface UserService {

    @TestBefore
    void testBefore();

    @TestAfter
    void testAfter();

    @TestAround
    void testAround();
}
