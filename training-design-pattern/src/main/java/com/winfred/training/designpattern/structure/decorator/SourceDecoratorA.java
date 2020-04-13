package com.winfred.training.designpattern.structure.decorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SourceDecoratorA extends Decorator {
    public SourceDecoratorA(ISource a) {
        super(a);
    }

    @Override
    public String doSomething() {
        // 功能增强
        before();
        // 调用父类方法
        String something = super.doSomething();
        // 功能增强
        after();
        return something;
    }

    private void before() {
        log.info("功能增强A - before");
    }

    private void after() {
        log.info("功能增强A - after");
    }
}
