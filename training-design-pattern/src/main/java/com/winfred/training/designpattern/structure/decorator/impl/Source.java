package com.winfred.training.designpattern.structure.decorator.impl;

import com.winfred.training.designpattern.structure.decorator.ISource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Source implements ISource {

    @Override
    public String doSomething() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[1];
        log.info("{}", element);
        return element.toString();
    }
}
