package com.winfred.training.designpattern.structure.proxy.staticproxy.impl;


import com.winfred.training.designpattern.structure.proxy.staticproxy.IProxyable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Proxyable implements IProxyable {
    @Override
    public void doSomething() {
        log.info("doSomething");
    }
}
