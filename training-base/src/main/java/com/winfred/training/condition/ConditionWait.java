package com.winfred.training.condition;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Slf4j
public class ConditionWait implements Runnable {
  private Lock lock;
  private Condition condition;
  
  
  public ConditionWait(Lock lock, Condition condition) {
    this.lock = lock;
    this.condition = condition;
  }
  
  @Override
  public void run() {
    lock.lock();
    try {
      log.info("{} -- {}", this.getClass().getSimpleName(), "begin");
      // 阻塞, 让出当前cpu
      condition.await();
      log.info("{} -- {}", this.getClass().getSimpleName(), "end");
    } catch (InterruptedException e) {
      log.error("", e);
    } finally {
      lock.unlock();
    }
    
  }
}
