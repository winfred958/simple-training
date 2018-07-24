package com.winfred.training.socket.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class TestNIOServer {

    private Selector selector;

    private MyServerProtocol myProtocol = new MyServerProtocolImpl(1024);

    private ServerSocketChannel serverSocketChannel;

    private void initialize() throws IOException {
//        selector = SelectorProvider.provider().openSelector();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9999));
        this.selector = Selector.open();
    }


    public void start() throws IOException {
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {

            if (selector.select(3000) == 0) {
                System.out.println("等待...");
                continue;
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isConnectable()) {
                    myProtocol.handleConnect(selectionKey);
                } else if (selectionKey.isAcceptable()) {
                    myProtocol.handleAccept(selectionKey);
                } else if (selectionKey.isReadable()) {
                    myProtocol.handleRead(selectionKey);
                } else if (selectionKey.isWritable()) {
                    myProtocol.handleWrite(selectionKey);
                }
                // 移除处理过的key
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) {
        TestNIOServer testNIOServer = new TestNIOServer();
        try {
            testNIOServer.initialize();
            testNIOServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
