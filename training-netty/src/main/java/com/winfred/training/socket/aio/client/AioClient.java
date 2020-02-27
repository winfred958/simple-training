package com.winfred.training.socket.aio.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;

@Slf4j
public class AioClient {

    private String hostname;
    private int port;

    private AsynchronousSocketChannel asynchronousSocketChannel;

    public AioClient(Builder builder) {
        this.hostname = builder.hostname;
        this.port = builder.port;
    }

    public void init() {
        try {
            asynchronousSocketChannel = AsynchronousSocketChannel.open();
            asynchronousSocketChannel.connect(new InetSocketAddress(hostname, port));
        } catch (IOException e) {
            log.error("", e);
        }
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
