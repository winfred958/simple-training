package com.winfred.training.designpattern.structure.proxy.staticp.impl;


import com.winfred.training.designpattern.structure.proxy.staticp.IA;

public class AProxy implements IA {
    private IA a;

    public AProxy(IA a) {
        this.a = a;
    }

    @Override
    public void doSomething() {
        // 代理逻辑
        a.doSomething();
        // 代理逻辑
    }
}
