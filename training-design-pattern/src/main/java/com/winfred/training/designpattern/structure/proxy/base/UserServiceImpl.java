package com.winfred.training.designpattern.structure.proxy.base;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public void testBefore() {
        log.info("{}: {}", this.getClass().getName(), "testBefore");
    }

    @Override
    public void testAfter() {
        log.info("{}: {}", this.getClass().getName(), "testAfter");
    }

    @Override
    public void testAround() {
        log.info("{}: {}", this.getClass().getName(), "testAround");
    }
}
