package com.winfred.training.socket.aio.server;


import com.winfred.training.core.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "TestAsyncServer")
public class TestAsyncServer {

    public static void main(String[] args) {
        AioServer asyncServer = new AioServer.Builder().setPort(19999).build();

        ThreadPoolUtil
                .doExecutor(() -> {
                    log.info("==========");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("==========");
                    asyncServer.stop();
                });
        log.info("========== start server");
        asyncServer.startServer();

        asyncServer.stop();
    }
}
