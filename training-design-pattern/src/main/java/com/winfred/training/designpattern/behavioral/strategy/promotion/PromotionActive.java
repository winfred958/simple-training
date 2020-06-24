package com.winfred.training.designpattern.behavioral.strategy.promotion;

public class PromotionActive {
  
  private PromotionStrategy promotionStrategy;
  
  public PromotionActive(PromotionStrategy promotionStrategy) {
    this.promotionStrategy = promotionStrategy;
  }
  
  public void doPromotion() {
    promotionStrategy.doPromotion();
  }
}
