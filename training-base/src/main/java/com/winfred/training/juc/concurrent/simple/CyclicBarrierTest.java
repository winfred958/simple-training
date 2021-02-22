package com.winfred.training.juc.concurrent.simple;

import com.winfred.training.juc.concurrent.pool.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 大家一起出发, 到达某个集合点, 然后大家再一起出发去下一个集合点
 *
 * @author winfred958
 */
@Slf4j
public class CyclicBarrierTest {

  /**
   * 可重复使用
   */
  private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

  public static void main(String[] args) {
    for (int i = 0; i < cyclicBarrier.getParties(); i++) {
      ThreadPoolUtil.doExecutor(new Task(cyclicBarrier, i));
    }
  }

  static class Task implements Runnable {

    private Integer number;
    private CyclicBarrier cyclicBarrier;

    public Task(CyclicBarrier cyclicBarrier, Integer number) {
      this.cyclicBarrier = cyclicBarrier;
      this.number = number;
    }

    @Override
    public void run() {
      final int parties = cyclicBarrier.getParties();
      for (int i = 0; i < parties; i++) {
        int randomNum = (int) (Math.random() * 1000) + 1000;
        try {
          Thread.sleep(randomNum);
          log.info("队员{} 到达集合点: {}", number, i);
          // 等待其他线程
          cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
          log.error("", e);
        }
      }
    }
  }
}
