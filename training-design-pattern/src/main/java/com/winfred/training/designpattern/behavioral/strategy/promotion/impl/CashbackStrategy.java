package com.winfred.training.designpattern.behavioral.strategy.promotion.impl;

import com.winfred.training.designpattern.behavioral.strategy.promotion.PromotionStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CashbackStrategy implements PromotionStrategy {
    @Override
    public void doPromotion() {
        log.info("返现: {}", "");
    }
}
