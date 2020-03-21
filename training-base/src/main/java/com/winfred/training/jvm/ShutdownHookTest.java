package com.winfred.training.jvm;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * JVM 优雅退出测试
 * 1. ShutdownHook在某些情况下不执行, 例如JVM崩溃, 无法接收信号量和 kill -9 pid 等.
 * 2. 当存在多个ShutDownHook时, JVM无法保证其执行顺序.
 * 3. 在JVM关闭期间不能动态添加或去除ShutdownHook.
 * 4. 不能在ShutDownHook中调用System.exit(), 它会卡住JVM, 导致进程无法退出.
 *
 * @author kevin
 */
@Slf4j
public class ShutdownHookTest {

    @Test
    public void shutdownHook() {

        log.info(" test java shutdown hook");

        Runtime
                .getRuntime()
                .addShutdownHook(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        log.info("start shutdown hook");
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        log.info("end shutdown hook");
                        // jvm ShutdownHook 中调用 System.exit(), 会卡住JVM, 导致进程无法退出.
                        //  System.exit(0);
                    }
                }));
    }
}
