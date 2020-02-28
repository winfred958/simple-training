package com.winfred.training.socket.nio.client;

import com.winfred.training.core.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

@Slf4j(topic = "client")
public class NioClientTest {

    private Selector selector;
    private SocketChannel socketChannel;

    private String hostname;
    private int port;
    private boolean isBlocking;

    public NioClientTest(Builder builder) {
        this.hostname = builder.hostname;
        this.port = builder.port;
        this.isBlocking = builder.isBlocking;
    }

    private void initialize() {
        // 打开信道并设置为非阻塞模式
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(hostname, port));
            socketChannel.configureBlocking(isBlocking);

            // 打开并注册到信道
            selector = Selector.open();

            // 注册感兴趣的 event
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            log.error("client init failed.", e);
        }

    }


    public void start() {

        while (true) {

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();

                try {
                    handler(key);
                } catch (IOException e) {
                    log.error("", e);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void reconnect() {
        boolean connected = false;
        try {
            connected = socketChannel.connect(new InetSocketAddress(hostname, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (connected) {
                socketChannel.register(selector, SelectionKey.OP_READ);
            } else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            socketChannel.close();
            selector.close();
        } catch (IOException e) {
            log.error("", e);
        }
    }

    public void sendMessage(String message) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));

        try {
            boolean finishConnect = socketChannel.finishConnect();
            if (finishConnect) {
                // 注册 reade event
                socketChannel.register(selector, SelectionKey.OP_READ);
            } else {
                reconnect();
            }

        } catch (ClosedChannelException e) {
            log.error("register", e);
        } catch (IOException e) {
            log.error("connect error", e);
        }
        if (null != socketChannel && socketChannel.isConnected()) {
            try {
                socketChannel.write(byteBuffer);
            } catch (IOException e) {
                log.error("[服务器已经断开]", e);
                // 重连
                initialize();
            }
        } else {
            log.info("服务器已经断开");
        }
    }

    private void handler(SelectionKey key) throws IOException {
        if (key.isConnectable()) {

            SocketChannel socketChannel = (SocketChannel) key.channel();
            if (socketChannel.finishConnect()) {
                socketChannel.register(selector, SelectionKey.OP_READ);
            }

        } else if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.clear();
            // 开始读取数据
            int length = socketChannel.read(byteBuffer);
            if (length <= 0) {
                return;
            }
            // 将缓冲区数据置为传出状态
            byteBuffer.flip();
            // 将字节转化为为UTF-8的字符串
            String receivedString = StandardCharsets.UTF_8.newDecoder().decode(byteBuffer).toString();

            SocketAddress remoteAddress = socketChannel.getRemoteAddress();
            log.info("{} >> {}", remoteAddress.toString(), receivedString);

            socketChannel.register(key.selector(), SelectionKey.OP_WRITE);
        }
    }


    public static class Builder {

        private String hostname;
        private int port;
        private boolean isBlocking;

        public NioClientTest build() {
            return new NioClientTest(this);
        }

        public Builder setHostName(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setIsBlocking(boolean isBlocking) {
            this.isBlocking = isBlocking;
            return this;
        }

    }

    public static void main(String[] args) {
        NioClientTest client = new Builder()
                .setHostName("127.0.0.1")
                .setPort(9999)
                .setIsBlocking(false)
                .build();


        client.initialize();

        ThreadPoolUtil
                .doExecutor(() -> {
                    client.start();
                });


        String str = null;
        while (!"exit".equals(String.valueOf(str).trim())) {
            Scanner scanner = new Scanner(System.in);
            str = scanner.nextLine();

            client.sendMessage(str);

        }


    }
}
