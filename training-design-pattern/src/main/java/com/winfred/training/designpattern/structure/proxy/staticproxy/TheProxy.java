package com.winfred.training.designpattern.structure.proxy.staticproxy;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TheProxy implements IProxyable {

  private IProxyable proxyable;

  public TheProxy(IProxyable proxyable) {
    this.proxyable = proxyable;
  }

  @Override
  public void doSomething() {
    // 代理逻辑
    log.info("--- before ---");
    proxyable.doSomething();
    // 代理逻辑
    log.info("--- after ---");
  }
}
