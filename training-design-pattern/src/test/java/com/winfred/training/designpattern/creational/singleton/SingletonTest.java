package com.winfred.training.designpattern.creational.singleton;

import org.junit.Test;

import java.util.concurrent.ExecutorService;

public class SingletonTest {

    @Test
    public void threadPoolTest() {
        ExecutorService instance1 = ThreadPoolUtil.getInstance();
        ExecutorService instance2 = ThreadPoolUtil.getInstance();
        ExecutorService instance3 = ThreadPoolUtil.getInstance();

        int max = 999;
        for (int i = 0; i < max; i++) {
            ThreadPoolUtil.doExecutor(new TestRunnable(i));
        }
    }

    class TestRunnable implements Runnable {
        private Integer i;

        public TestRunnable(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println(i);
        }
    }
}
