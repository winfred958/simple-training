package com.winfred.training.designpattern.structure.proxy.base;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceImpl implements UserService {
  @Override
  public void testBefore(ProxyEntity entity) {
    log.info("{}: {} {}", this.getClass().getName(), "testBefore", entity.getName());
  }
  
  @Override
  public void testAfter(ProxyEntity entity) {
    log.info("{}: {} {}", this.getClass().getName(), "testAfter", entity.getName());
  }
  
  @Override
  public void testAround(ProxyEntity entity) {
    log.info("{}: {} {}", this.getClass().getName(), "testAround", entity.getName());
  }
}
