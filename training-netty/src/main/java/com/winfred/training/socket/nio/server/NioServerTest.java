package com.winfred.training.socket.nio.server;

import com.winfred.training.core.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * NIO (No-Bloking IO)
 * <p>
 * 1. 打开 ServerSocketChannel, 用于监听客户端连接
 * 2. 绑定监听端口, 设置非阻塞模式
 * 3. 创建Selector, 启动线程
 * 4. 将 ServerSocketChannel注册到Selector, 监听OP_ACCEPT事件
 * 5. Selector 轮询监听的 key,
 * 6. handleAcc() 处理新的客户端接入, Selector监听到有新的客户端接入, 处理请求, 完成TCP三次握手, 建立物理链路
 * 7. 设置新客户端连接的参数, 为非阻塞模式
 * 8. 将新连接注册到 Selector, 监听 SelectionKey.OP_READ
 * 9. 异步读取消息到ByteBuffer
 * 10. decode, 如果有半包指针reset, 继续读取后续的报文, 将解码成功的消息封装成Task, 投递到业务线程池中, 处理请求
 * 11. 返回值异步写ByteBuffer, 调用SocketChannel的write方法, 将消息一步发送给客户端
 */

@Slf4j(topic = "server")
public class NioServerTest {

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


    public void start() {
        //4.将serverSocketChannel注册到Reactor线程的多路复用器Selector上, 监听 ACCEPT事件
        try {
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (ClosedChannelException e) {
            log.error(" [serverSocketChannel.register] ", e);
        }
        while (true) {

            try {
                if (selector.select(3000) == 0) {
                    log.info("没有事件发生, 等待...");
                    continue;
                }
            } catch (IOException e) {
                log.error("", e);
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
        NioServerTest testNIOServer = new NioServerTest();
        try {
            testNIOServer.initialize();
            testNIOServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
