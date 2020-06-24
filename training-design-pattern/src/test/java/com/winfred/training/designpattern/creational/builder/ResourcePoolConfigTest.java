package com.winfred.training.designpattern.creational.builder;

import org.junit.Test;

public class ResourcePoolConfigTest {
  
  @Test
  public void testBuilder() {
    ResourcePoolConfig resourcePoolConfig = new ResourcePoolConfig.Builder()
            .setName("builder-test")
            .setCoreSize(8)
            .setMaxSize(16)
            .build();
    
    resourcePoolConfig.getCoreSize();
  }
}
