package com.winfred.training.designpattern.structure.decorator.general;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SourceDecoratorAddSausage extends Decorator {
  public SourceDecoratorAddSausage(ISource a) {
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
    msg = msg + " +1香肠";
    return msg;
  }
  
  private Double changePrice(Double price) {
    price = price + 1;
    return price;
  }
}
