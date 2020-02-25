package com.winfred.training.socket.nio.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;

@Slf4j(topic = "client")
public class NIOClientTest {

    private Selector selector;
    private SocketChannel socketChannel;

    private String hostname;
    private int port;
    private boolean isBlocking;

    public NIOClientTest(Builder builder) {
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
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            log.error("client init failed.", e);
        }

    }

    public void start() {
        initialize();
        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
        while (keyIterator.hasNext()) {
            SelectionKey selectionKey = keyIterator.next();
            keyIterator.remove();
            try {
                handler(selectionKey);
            } catch (IOException e) {
                log.error("", e);
            }
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
        ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes(Charset.defaultCharset()));
        if (null != socketChannel && socketChannel.isConnected()) {
            try {
                socketChannel.write(byteBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.info("服务器已经断开");
        }
        // 注册 reade event
        try {
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
    }

    private void handler(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            // 建立 socket channel (双工)
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            // 注册, 监听 read event
            socketChannel.register(key.selector(), SelectionKey.OP_READ);

        } else if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.clear();
            // 开始读取数据
            int length = socketChannel.read(byteBuffer);
            if (length == -1) {
                return;
            }
            // 将缓冲区数据置为传出状态
            byteBuffer.flip();
            // 将字节转化为为UTF-8的字符串
            String receivedString = Charset.defaultCharset().newDecoder().decode(byteBuffer).toString();

            SocketAddress remoteAddress = socketChannel.getRemoteAddress();
            log.info("{} >> {}", remoteAddress.toString(), receivedString);

            socketChannel.register(key.selector(), SelectionKey.OP_WRITE);
        } else if (key.isWritable()) {
        }
    }


    public static class Builder {

        private String hostname;
        private int port;
        private boolean isBlocking;

        public NIOClientTest build() {
            return new NIOClientTest(this);
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
        NIOClientTest client = new Builder()
                .setHostName("127.0.0.1")
                .setPort(9999)
                .setIsBlocking(false)
                .build();

        client.start();

        String str = null;
        while (!"exit".equals(String.valueOf(str).trim())) {
            Scanner scanner = new Scanner(System.in);
            str = scanner.nextLine();
            client.sendMessage(str);
        }
        client.stop();
    }
}
