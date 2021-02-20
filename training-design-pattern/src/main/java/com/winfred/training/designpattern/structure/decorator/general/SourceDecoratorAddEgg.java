package com.winfred.training.designpattern.structure.decorator.general;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SourceDecoratorAddEgg extends Decorator {
  public SourceDecoratorAddEgg(ISource a) {
    super(a);
  }

  @Override
  public String getMsg() {
    String msg = super.getMsg();
    return changeMsg(msg);
  }

  @Override
  public Double getPrice() {
    Double price = super.getPrice();
    return changePrice(price);
  }

  private String changeMsg(String msg) {
    msg = msg + " +1鸡蛋";
    return msg;
  }

  private Double changePrice(Double price) {
    price = price + 2;
    return price;
  }
}
