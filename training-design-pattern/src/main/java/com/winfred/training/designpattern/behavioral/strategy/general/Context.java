package com.winfred.training.designpattern.behavioral.strategy.general;

/**
 * 策略选择上下文, 又用户实现
 * 单一委派
 */
public class Context {

  private Strategy strategy;

  public Context(Strategy strategy) {
    this.strategy = strategy;
  }

  public void algorithm() {
    strategy.algorithm();
  }
}
