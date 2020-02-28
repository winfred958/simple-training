package com.winfred.training.socket.aio.client;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class AioClientTest {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {

        AioClient aioClient = new AioClient.Builder()
                .setHostname("127.0.0.1")
                .setPort(19999)
                .build();

        Future<Void> future = aioClient.init();
        try {
            future.get();
            log.info("client connected ..........");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        aioClient.sendMessage("123456");

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
