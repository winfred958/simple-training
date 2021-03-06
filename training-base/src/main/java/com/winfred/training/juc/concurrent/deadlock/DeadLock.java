package com.winfred.training.juc.concurrent.deadlock;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DeadLock {

//    Found one Java-level deadlock:
//    =============================
//    "thread1":
//    waiting to lock monitor 0x0000000005249858 (object 0x00000000d61971c8, a java.lang.String),
//    which is held by "thread2"
//    "thread2":
//    waiting to lock monitor 0x000000000524bed8 (object 0x00000000d6197190, a java.lang.String),
//    which is held by "thread1"
//
//
//    Java stack information for the threads listed above:
//    ===================================================
//    "thread1":
//    at com.winfred.training.thread.deadlock.DeadLock$DeadLockSimple.run(DeadLock.java:25)
//            - waiting to lock <0x00000000d61971c8> (a java.lang.String)
//            - locked <0x00000000d6197190> (a java.lang.String)
//    at java.lang.Thread.run(Thread.java:748)
//    "thread2":
//    at com.winfred.training.thread.deadlock.DeadLock$DeadLockSimple.run(DeadLock.java:25)
//            - waiting to lock <0x00000000d6197190> (a java.lang.String)
//            - locked <0x00000000d61971c8> (a java.lang.String)
//    at java.lang.Thread.run(Thread.java:748)
//
//
//    Found 1 deadlock.

  static class DeadLockSimple implements Runnable {

    private String lock1;
    private String lock2;

    public DeadLockSimple(String lock1, String lock2) {
      this.lock1 = lock1;
      this.lock2 = lock2;
    }

    @Override
    public void run() {
      synchronized (lock1) {
        log.info(Thread.currentThread().getName() + " obtained " + lock1);
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        synchronized (lock2) {
          log.info(Thread.currentThread().getName() + " obtained " + lock2);
        }
      }
    }
  }

  public static void main(String[] args) {

    ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        return new Thread("single scheduler pool");
      }
    });
    // 每隔10s 检测一次该jvm 死锁
    scheduledExecutorService
        .scheduleAtFixedRate(new Runnable() {
          @Override
          public void run() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            log.info("================ WARNING ==================== " + simpleDateFormat.format(Calendar.getInstance().getTime()));

            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            // 获取死锁线程thread id
            long[] tids = threadMXBean.findDeadlockedThreads();
            ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(tids);
            for (ThreadInfo threadInfo : threadInfos) {

              log.info("deadlock thread :: " + threadInfo.getThreadName() +
                  " :: lockName=" + threadInfo.getLockName() +
                  " | ThreadId=" + threadInfo.getThreadId() +
                  " | LockOwnerId=" + threadInfo.getLockOwnerId() +
                  " | LockOwnerName=" + threadInfo.getLockOwnerName());
            }

//                showMemoryInfo();

          }
        }, 10L, 10L, TimeUnit.SECONDS);

    String lock1 = "lock1";
    String lock2 = "lock2";

    Thread thread1 = new Thread(new DeadLockSimple(lock1, lock2), "thread1");
    Thread thread2 = new Thread(new DeadLockSimple(lock2, lock1), "thread2");

    thread1.start();
    thread2.start();

//        try {
//            thread1.join();
//            thread2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


  }


  public static void showMemoryInfo() {
    MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();

    log.info(
        "heapMemoryUsage max " + heapMemoryUsage.getMax() +
            "\nheapMemoryUsage init " + heapMemoryUsage.getInit() +
            "\nheapMemoryUsage used " + heapMemoryUsage.getUsed()
    );

    MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
    nonHeapMemoryUsage.getMax();
    nonHeapMemoryUsage.getInit();
    nonHeapMemoryUsage.getUsed();
    log.info(
        "nonHeapMemoryUsage max " + nonHeapMemoryUsage.getMax() +
            "\nnonHeapMemoryUsage init " + nonHeapMemoryUsage.getInit() +
            "\nnonHeapMemoryUsage used " + nonHeapMemoryUsage.getUsed()
    );
  }
}

