package com.winfred.training.designpattern.creational.singleton;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 单例模式: 静态内部类实现方式
 * <p>
 * 类 Initialization 时机:
 * 1. JVM 在执行new(Create new object), getstatic/putstatic(Get/Set static field in class), invokestatic(反射调用类静态方法, Invoke a class static method)
 * <p>
 * 利用class的 initialization lock, 线程安全
 *
 * @author kevin
 */
public class ThreadPoolUtil {
  
  private static Integer CORE_POOL_SIZE = 64;
  private static Integer MAX_POOL_SIZE = 1024;
  
  private ThreadPoolUtil() {
    
  }
  
  private static class SingleClass {
    private static ExecutorService executor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            30L,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(MAX_POOL_SIZE * 4),
            new ThreadFactory() {
              private final AtomicLong tid = new AtomicLong(1);
              
              @Override
              public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "ud-thread-pool-" + tid.getAndDecrement());
                return thread;
              }
            }
    );
  }
  
  public static ExecutorService getInstance() {
    return SingleClass.executor;
  }
  
  public static Future<?> doExecutor(Callable<?> callable) {
    return getInstance().submit(callable);
  }
  
  public static void doExecutor(Runnable runnable) {
    getInstance().execute(runnable);
  }
}
