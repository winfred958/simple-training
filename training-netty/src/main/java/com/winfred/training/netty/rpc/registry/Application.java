package com.winfred.training.netty.rpc.registry;

import java.util.concurrent.CountDownLatch;

public class Application {
  private static CountDownLatch countDownLatch = new CountDownLatch(1);

  public static void main(String[] args) {

    Registry registry = new Registry.Builder()
        .setPort(8080)
        .setBoosPoolSize(16)
        .setWorkerPoolSize(64)
        .build();

    registry.start();

    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
