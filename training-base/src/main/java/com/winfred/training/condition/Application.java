package com.winfred.training.condition;

import com.winfred.training.base.ThreadPoolUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Application {
  
  private static CountDownLatch countDownLatch = new CountDownLatch(1);
  
  public static void main(String[] args) {
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    
    ThreadPoolUtil
            .doExecutor(new ConditionWait(lock, condition));
    ThreadPoolUtil
            .doExecutor(new ConditionNotify(lock, condition));
    
    try {
      countDownLatch.await(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      countDownLatch.countDown();
      ThreadPoolUtil.destroy();
    }
  }
}
