package com.winfred.training.designpattern.creational.factory.method;

public class ACar implements ICar {
  @Override
  public void goAhead(Integer speed) {
    System.out.println("A go ahead: " + speed + "km/h");
  }

}
