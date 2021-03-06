package com.winfred.training.juc.concurrent.pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 指定容量的线程池
 *
 * @author kevin
 */
public final class ThreadPoolUtil {

  private static int PROCESS = Runtime.getRuntime().availableProcessors();

  // 计算密集型
  private static Integer CORE_POOL_SIZE = PROCESS - 1;

  // IO密集型
  private static Integer MAX_POOL_SIZE = PROCESS * 2;

  private ThreadPoolUtil() {

  }

  private static class SingleClass {
    private static ExecutorService executor = new ThreadPoolExecutor(
        CORE_POOL_SIZE, MAX_POOL_SIZE,
        30L, TimeUnit.SECONDS,
        new LinkedBlockingDeque<>(MAX_POOL_SIZE * 1024),
        new ThreadFactory() {
          private final AtomicLong tid = new AtomicLong(0);
          private static final String name = "ud-pool-1-thread-";

          @Override
          public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, name + tid.incrementAndGet());
            return thread;
          }
        }
    );
  }

  public static ExecutorService getInstance() {
    return SingleClass.executor;
  }

  public static Future doExecutor(Callable callable) {
    return getInstance().submit(callable);
  }

  public static void doExecutor(Runnable runnable) {
    getInstance().execute(runnable);
  }

  public static void destroy() {
    getInstance().shutdown();
  }
}
