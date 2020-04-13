package com.winfred.training.designpattern.structure.decorator;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class Decorator implements ISource {
    private ISource source;

    public Decorator(ISource a) {
        this.source = a;
    }

    @Override
    public String doSomething() {
        return source.doSomething();
    }

}
