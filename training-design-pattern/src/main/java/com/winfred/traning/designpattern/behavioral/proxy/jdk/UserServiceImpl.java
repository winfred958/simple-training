package com.winfred.traning.designpattern.behavioral.proxy.jdk;

public class UserServiceImpl implements UserService {
    @Override
    public void testBefore() {
        System.out.println(String.format("%s : %s", this.getClass().getName(), "testBefore"));
    }

    @Override
    public void testAfter() {
        System.out.println(String.format("%s : %s", this.getClass().getName(), "testAfter"));
    }

    @Override
    public void testAround() {
        System.out.println(String.format("%s : %s", this.getClass().getName(), "testAround"));
    }
}
