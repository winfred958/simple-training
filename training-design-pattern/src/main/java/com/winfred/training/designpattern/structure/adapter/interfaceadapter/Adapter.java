package com.winfred.training.designpattern.structure.adapter.interfaceadapter;

public class Adapter implements Target {
  
  private Source source;
  
  public Adapter(Source source) {
    this.source = source;
  }
  
  @Override
  public int output5v() {
    return source.outputVoltage() / 44;
  }
  
  @Override
  public int output12v() {
    return source.outputVoltage() / 18;
  }
}
