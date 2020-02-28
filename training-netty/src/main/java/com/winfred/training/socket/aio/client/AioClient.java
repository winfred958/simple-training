package com.winfred.training.socket.aio.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

@Slf4j
public class AioClient {

    private String hostname;
    private int port;

    private AsynchronousSocketChannel asynchronousSocketChannel;

    public AioClient(Builder builder) {
        this.hostname = builder.hostname;
        this.port = builder.port;
    }

    public Future<Void> init() {
        Future<Void> connect = null;
        try {
            asynchronousSocketChannel = AsynchronousSocketChannel.open();
            connect = asynchronousSocketChannel.connect(new InetSocketAddress(hostname, port));
        } catch (IOException e) {
            log.error("", e);
        }
        return connect;
    }


    public void sendMessage(String msg) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(msg.getBytes(StandardCharsets.UTF_8));
        buffer.flip();

        log.info("[client send message]: {}", msg);
        asynchronousSocketChannel.write(buffer);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        asynchronousSocketChannel
                .read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        attachment.flip();
                        // 将字节转化为为UTF-8的字符串
                        try {
                            String responseStr = StandardCharsets.UTF_8.newDecoder().decode(attachment).toString();
                            log.info("[ RESPONSE ]: {}", responseStr);
                        } catch (CharacterCodingException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        log.error("", exc);
                    }
                });
    }


    public static class Builder {
        private String hostname;
        private int port;

        public Builder setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public AioClient build() {
            return new AioClient(this);
        }
    }
}
