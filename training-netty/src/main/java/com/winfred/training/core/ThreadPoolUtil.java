package com.winfred.training.core;

import java.util.concurrent.*;

/**
 * 指定容量的线程池
 *
 * @author kevin
 */
public final class ThreadPoolUtil {
    private static ExecutorService executorService;

    private static Integer CORE_POOL_SIZE = 8;
    private static Integer MAX_POOL_SIZE = 2048;

    private static class SingleClass {
        private static ExecutorService executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAX_POOL_SIZE,
                30L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(MAX_POOL_SIZE * 2));
    }

    private static ExecutorService getInstance() {
        if (null == executorService) {
            executorService = SingleClass.executor;
        }
        return executorService;
    }

    public static Future doExecutor(Callable callable) {
        getInstance();
        return executorService.submit(callable);
    }

    public static void doExecutor(Runnable runnable) {
        getInstance();
        executorService.execute(runnable);
    }
}
