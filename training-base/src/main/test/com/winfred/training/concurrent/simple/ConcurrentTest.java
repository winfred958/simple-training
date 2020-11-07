package com.winfred.training.concurrent.simple;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ConcurrentTest {

  private int count = 0;

  private int max = 100000;

  @Test
  public void concurrentTest() {

    new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < max; i++)
          incrUnsafe(1);
      }
    }).start();


    new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < max; i++)
          incrUnsafe(1);
      }
    }).start();


    new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < max; i++)
          incrUnsafe(1);
      }
    }).start();


    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    log.info("============================================");
    log.info("count: {}", count);
    log.info("============================================");
  }

  public synchronized void incrSync(int value) {
    this.count += value;
  }

  public void incrUnsafe(int value) {
    this.count += value;
  }

}
