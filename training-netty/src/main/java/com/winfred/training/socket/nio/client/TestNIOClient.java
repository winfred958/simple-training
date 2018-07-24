package com.winfred.training.socket.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestNIOClient {

    private static ExecutorService executorService = Executors.newCachedThreadPool();


    static class NIOClient implements Callable {
        private Selector selector;
        private SocketChannel socketChannel;


        private String hostName = "127.0.0.1";

        private int port = 9999;

        private String message;

        public NIOClient(String message) {
            this.message = message;
        }

        @Override
        public Object call() throws Exception {
            initialize();
            sendMessage(message);
            return null;
        }

        private void initialize() throws IOException {

            // 打开信道并设置为非阻塞模式
            socketChannel = SocketChannel.open(new InetSocketAddress(hostName, port));
            socketChannel.configureBlocking(false);

            // 打开并注册到信道
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_READ);
        }

        public void reconnection() throws IOException {
            initialize();
        }

        public void sendMessage(String message) throws IOException {
            ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes(Charset.defaultCharset()));
            if (null != socketChannel && socketChannel.isConnected()) {
                socketChannel.write(byteBuffer);
                // FIXME: 启动客户端信道监听线程
                executorService.submit(new ClientListener(selector));
            } else {
                System.out.println("服务器已经断开");
            }
        }
    }


    static class ClientListener implements Callable {

        private Selector selector;

        public ClientListener(Selector selector) {
            this.selector = selector;
        }

        @Override
        public Object call() throws Exception {

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isConnectable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    if (channel.isConnectionPending()) {
                        channel.finishConnect();
                    }
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);

                } else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    channel.read(byteBuffer);
                    byteBuffer.flip();

                    // 将字节转化为为UTF-16的字符串
                    String receivedString = Charset.defaultCharset().newDecoder().decode(byteBuffer).toString();

                    // 控制台打印出来
                    System.out.println(receivedString);

                }
                keyIterator.remove();
            }
            return null;
        }
    }

    public static void main(String[] args) {



        System.out.println("开始发送信息");

        String str = null;

        while (!"exit".equals(String.valueOf(str).trim())) {
            Scanner scanner = new Scanner(System.in);
            str = scanner.nextLine();
            try {
                executorService.submit(new NIOClient(str));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

