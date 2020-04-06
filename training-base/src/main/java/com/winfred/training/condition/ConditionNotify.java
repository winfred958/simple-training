package com.winfred.training.condition;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Slf4j
public class ConditionNotify implements Runnable {
    private Lock lock;
    private Condition condition;


    public ConditionNotify(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            log.info("{} -- {}", this.getClass().getSimpleName(), "begin");
            // 唤醒阻塞状态的线程
            condition.signal();
            log.info("{} -- {}", this.getClass().getSimpleName(), "end");
        } finally {
            lock.unlock();
        }
    }
}
