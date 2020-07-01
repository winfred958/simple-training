package com.winfred.elastic.common;

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
        private static ForkJoinPool forkJoin = new ForkJoinPool(2048,
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        t.interrupt();
                        log.error(" fork/join task error: {} - {} : {}", t.getId(), t.getName(), e);
                    }
                },
                true
        );
    }

    public static ForkJoinPool getInstance() {
        return forkJoinPool;
    }
}
