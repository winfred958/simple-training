package com.winfred.training.thread.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
                System.out.println(Thread.currentThread().getName() + " obtained " + lock1);
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + " obtained " + lock2);
                }
            }
        }
    }

    public static void main(String[] args) {
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

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        // 每隔10s 检测一次该jvm 死锁
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("================ WARNING ==================== " + simpleDateFormat.format(Calendar.getInstance().getTime()));

                ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                // 获取死锁线程thread id
                long[] tids = threadMXBean.findDeadlockedThreads();
                ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(tids);
                for (ThreadInfo threadInfo : threadInfos) {
                    System.out.println("deadlock thread: " + threadInfo.getThreadName());
                }
            }
        }, 10L, 10L, TimeUnit.SECONDS);


    }


}

