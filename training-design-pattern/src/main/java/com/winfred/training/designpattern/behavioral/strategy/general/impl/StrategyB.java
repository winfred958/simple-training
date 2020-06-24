package com.winfred.training.designpattern.behavioral.strategy.general.impl;

import com.winfred.training.designpattern.behavioral.strategy.general.Strategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StrategyB implements Strategy {
  @Override
  public void algorithm() {
    log.info("{}", this.getClass().getName());
  }
}
