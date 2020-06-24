package com.winfred.training.designpattern.creational.factory.method;

public class BCar implements ICar {
  @Override
  public void goAhead(Integer speed) {
    System.out.println("B go ahead: " + speed + "km/h");
  }
  
}
