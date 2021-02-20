package com.winfred.training.designpattern.behavioral.strategy.promotion;

import com.winfred.training.designpattern.behavioral.strategy.promotion.impl.CashbackStrategy;
import com.winfred.training.designpattern.behavioral.strategy.promotion.impl.CouponStrategy;
import com.winfred.training.designpattern.behavioral.strategy.promotion.impl.EmptyStrategy;
import com.winfred.training.designpattern.behavioral.strategy.promotion.impl.GroupbuyStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 策略工厂
 */
public class PromotionStrategyFactory {


  private static class Singleton {

    private static Map<String, PromotionStrategy> promotionStrategyMap = new ConcurrentSkipListMap();

    static {
      promotionStrategyMap.put(PromotionKey.COUPON, new CouponStrategy());
      promotionStrategyMap.put(PromotionKey.CASHBACK, new CashbackStrategy());
      promotionStrategyMap.put(PromotionKey.GROUPBUY, new GroupbuyStrategy());
      promotionStrategyMap.put(PromotionKey.EMPTY, new EmptyStrategy());
    }
  }

  private PromotionStrategyFactory() {
  }

  public static PromotionStrategy getPromotion(String promotionKey) {
    return Singleton.promotionStrategyMap.get(promotionKey);
  }

  public interface PromotionKey {
    String EMPTY = "EMPTY";
    String COUPON = "COUPON";
    String CASHBACK = "CASHBACK";
    String GROUPBUY = "GROUPBUY";
  }
}
