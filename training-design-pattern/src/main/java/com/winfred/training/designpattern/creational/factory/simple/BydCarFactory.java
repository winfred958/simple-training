package com.winfred.training.designpattern.creational.factory.simple;

import lombok.extern.slf4j.Slf4j;

/**
 * 简单工厂模式
 * <p>
 * 缺点: 不易扩展过于复杂的产品结构
 */
@Slf4j
public class BydCarFactory {
  
  public Car buildCar(Class<? extends Car> clazz) {
    try {
      return clazz.newInstance();
    } catch (InstantiationException e) {
      log.error("", e);
    } catch (IllegalAccessException e) {
      log.error("", e);
    }
    return null;
  }
}
