package com.winfred.training.designpattern.creational.factory.simple;

public class BydQin implements Car {
  @Override
  public void goAhead(Integer speed) {
    System.out.println("BydQin go ahead: " + speed + "km/h");
  }
  
  @Override
  public void turnTo(Integer speed, Double angle) {
    
  }
  
  @Override
  public void backOff(Integer speed, Double angle) {
    
  }
}
