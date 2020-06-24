package com.winfred.training.designpattern.structure.adapter.interfaceadapter;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class AdapterClient {
  
  @Test
  public void adapterTest() {
    Source source = new Source();
    Adapter target = new Adapter(source);
    int output = target.output5v();
    log.info("{}", output);
  }
}
