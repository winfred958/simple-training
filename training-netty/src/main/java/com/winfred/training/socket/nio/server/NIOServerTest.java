package com.winfred.training.socket.nio.server;

import com.winfred.training.core.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * NIO (No-Bloking IO)
 */
@Slf4j(topic = "server")
public class NIOServerTest {

    private Selector selector;

    private MyServerProtocol myProtocol = new MyServerProtocolImpl(1024);

    private ServerSocketChannel serverSocketChannel;

    private void initialize() throws IOException {
        // 1. ServerSocketChannel, 监听客户端连接, 所用客户端连接的副管道
        serverSocketChannel = ServerSocketChannel.open();
        // 2. 绑定端口, 设置连接为非阻塞模式
        serverSocketChannel.bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
        // 3. 创建多路复用器
        this.selector = Selector.open();
    }


    public void start() throws IOException {
        //4.将serverSocketChannel注册到Reactor线程的多路复用器Selector上, 监听 ACCEPT事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {

            if (selector.select(3000) == 0) {
                log.info("等待...");
                continue;
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 移除处理过的事件
                iterator.remove();
                // 开始处理事件
                if (selectionKey.isConnectable()) {
                    myProtocol.handleConnect(selectionKey);
                } else if (selectionKey.isAcceptable()) {
                    myProtocol.handleAccept(selectionKey);
                } else if (selectionKey.isReadable()) {
                    ThreadPoolUtil.doExecutor(() -> {
                        myProtocol.handleRead(selectionKey);
                    });
                } else if (selectionKey.isWritable()) {
                    ThreadPoolUtil.doExecutor(() -> {
                        try {
                            myProtocol.handleWrite(selectionKey, "");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }

    public static void main(String[] args) {
        NIOServerTest testNIOServer = new NIOServerTest();
        try {
            testNIOServer.initialize();
            testNIOServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
