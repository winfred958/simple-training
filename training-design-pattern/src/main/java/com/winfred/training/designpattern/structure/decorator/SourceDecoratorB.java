package com.winfred.training.designpattern.structure.decorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SourceDecoratorB extends Decorator {
    public SourceDecoratorB(ISource a) {
        super(a);
    }

    @Override
    public String doSomething() {
        // 功能增强
        before();
        String something = super.doSomething();
        // 功能增强
        after();
        return something;
    }

    private void before() {
        log.info("功能增强B - before");
    }

    private void after() {
        log.info("功能增强B - after");
    }
}
