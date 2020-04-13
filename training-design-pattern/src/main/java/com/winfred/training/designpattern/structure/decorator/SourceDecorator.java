package com.winfred.training.designpattern.structure.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * 装饰器模式 (Decorator Pattern), 包装器模式
 * <p>
 * 指再不改变原有对象的基础上, 将功能附加到对象上, 提供了比继承更有弹性的替代方案.
 */
@Slf4j
public class SourceDecorator implements ISource {
    private ISource source;

    public SourceDecorator(ISource a) {
        this.source = a;
    }

    @Override
    public String doSomething() {
        // 功能增强
        log.info("功能增强");
        source.doSomething();
        // 功能增强
        log.info("功能增强");
        return null;
    }
}
