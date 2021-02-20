package com.winfred.training.designpattern.behavioral.strategy.promotion;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class StrategyClient {

  private String promotionKey = "COUPON";

  @Test
  public void strategyTest() {

    PromotionStrategy promotionStrategy = PromotionStrategyFactory.getPromotion(promotionKey);
    PromotionActive promotionActive = new PromotionActive(promotionStrategy);
    promotionActive.doPromotion();

  }
}
