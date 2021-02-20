package com.winfred.training.juc.concurrent.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;

/**
 * ForkJoin pool
 *
 * @author kevin
 * @since 2018/7/27 10:19
 */
@Slf4j
public class ForkJoinUtils {

  private static ForkJoinPool forkJoinPool = Single.forkJoin;

  private static class Single {

    private static int maxWorks = 2048;

    private static ForkJoinPool forkJoin = new ForkJoinPool(maxWorks - 1,
        ForkJoinPool.defaultForkJoinWorkerThreadFactory,
        new Thread.UncaughtExceptionHandler() {
          @Override
          public void uncaughtException(Thread t, Throwable e) {
            t.interrupt();
            log.error(" fork/join task error: {} - {} : {}", t.getId(), t.getName(), e);
          }
        },
        true
        // @since 1.9 +
//                ,
//                64,
//                maxWorks,
//                64,
//                null,
//                30,
//                TimeUnit.SECONDS
    );
  }

  public static ForkJoinPool getInstance() {
    return forkJoinPool;
  }
}
