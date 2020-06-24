package com.winfred.training.designpattern.behavioral.strategy.general;

import com.winfred.training.designpattern.behavioral.strategy.general.impl.StrategyA;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class StrategyClient {
  
  @Test
  public void strategyTest() {
    Strategy strategy = new StrategyA();
    Context context = new Context(strategy);
    
    context.algorithm();
  }
}
