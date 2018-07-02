package com.winfred.training.thread.deadlock;

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

    }
}

