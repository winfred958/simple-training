package com.winfred.training.designpattern.structure.proxy.staticproxy;

import com.winfred.training.designpattern.structure.proxy.staticproxy.impl.Proxyable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class StaticProxyClient {

    @Test
    public void staticProxyTest() {
        // 注入被代理类
        IProxyable proxyable = new TheProxy(new Proxyable());
        proxyable.doSomething();
    }
}
