package com.winfred.training.designpattern.creational.singleton;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;

@Slf4j
public class SingletonTest {
  
  @Test
  public void threadPoolTest() {
    
    ExecutorService instance1 = ThreadPoolUtil.getInstance();
    ExecutorService instance2 = ThreadPoolUtil.getInstance();
    ExecutorService instance3 = ThreadPoolUtil.getInstance();
    
    int max = 999;
    for (int i = 0; i < max; i++) {
      ThreadPoolUtil.doExecutor(new TestRunnable(i));
    }
  }
  
  @Test
  public void hungrySingletonTest() {
    int hashcode1 = HungrySingleton.getHashcode();
    int hashcode2 = HungrySingleton.getHashcode();
    int hashcode3 = HungrySingleton.getHashcode();
  }
  
  @Test
  public void hungrySingletonTestV2() {
    int hashcode1 = HungrySingletonV2.getHashcode();
    int hashcode2 = HungrySingletonV2.getHashcode();
    int hashcode3 = HungrySingletonV2.getHashcode();
  }
  
  
  class TestRunnable implements Runnable {
    private Integer i;
    
    public TestRunnable(int i) {
      this.i = i;
    }
    
    @Override
    public void run() {
      log.info("{}", i);
    }
  }
}
