package com.winfred.training.designpattern.structure.decorator.impl;

import com.winfred.training.designpattern.structure.decorator.IA;

public class ADecorator implements IA {
    private IA a;

    public ADecorator(IA a) {
        this.a = a;
    }

    @Override
    public void doSomething() {
        // 功能增强
        a.doSomething();
        // 功能增强
    }
}
