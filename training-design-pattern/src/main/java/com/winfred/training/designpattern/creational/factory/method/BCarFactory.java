package com.winfred.training.designpattern.creational.factory.method;

public class BCarFactory implements ICarFactory {
  @Override
  public ICar create() {
    return new BCar();
  }
}
