package com.winfred.training.socket.aio.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author kevin
 */
@Slf4j
public class AsyncServer {

    private int port;

    /**
     * 使用栅栏, 控制异步, 不然主线程会直接退出
     */
    CountDownLatch countDownLatch = new CountDownLatch(1);

    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncServer(Builder builder) {
        this.port = builder.port;
    }

    public void startServer() {
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));

            log.info("server starting at port {}", port);

            doAccept();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error("CountDownLatch InterruptedException: ", e);
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    public void stop() {
        countDownLatch.countDown();
    }

    /**
     * 处理客户端连接
     */
    private void doAccept() {
        asynchronousServerSocketChannel
                .accept(this, new CompletionHandler<AsynchronousSocketChannel, AsyncServer>() {
                    @Override
                    public void completed(AsynchronousSocketChannel result, AsyncServer attachment) {
                        attachment.asynchronousServerSocketChannel.accept(attachment, this);

                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        result.read(byteBuffer, byteBuffer, new ReadCompletionHandler(result));
                    }

                    @Override
                    public void failed(Throwable exc, AsyncServer asyncServer) {
                        log.error("[ Asnc Server failed ]", exc);
                        asyncServer.stop();
                    }
                });
    }


    public static class Builder {
        private int port;

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public AsyncServer build() {
            return new AsyncServer(this);
        }
    }
}
