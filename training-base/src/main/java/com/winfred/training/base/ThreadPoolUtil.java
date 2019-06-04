package com.winfred.training.base;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 指定容量的线程池
 *
 * @author kevin
 */
public final class ThreadPoolUtil {
    private static ExecutorService executorService;

    private static Integer CORE_POOL_SIZE = 64;
    private static Integer MAX_POOL_SIZE = 2048;

    private static class SingleClass {
        private static ExecutorService executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAX_POOL_SIZE,
                30L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(MAX_POOL_SIZE * 2),
                new ThreadFactory() {
                    private final AtomicLong tid = new AtomicLong(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r, "udf-thread-pool-" + tid.getAndDecrement());
                        return thread;
                    }
                }
        );
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

    public static void destroy() {
        executorService.shutdown();
    }
}
