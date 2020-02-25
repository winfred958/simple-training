package com.winfred.training.socket.nio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;


public interface MyServerProtocol {

    default void serverPrint(SocketChannel socketChannel, String str) {
        Logger log = LoggerFactory.getLogger(this.getClass());
        try {
            SocketAddress remoteAddress = socketChannel.getRemoteAddress();
            log.info("{} >> {}", remoteAddress.toString(), str);
        } catch (IOException e) {
            log.error("server print error", e);
        }
    }

    void handleConnect(SelectionKey key) throws IOException;

    /**
     * 处理客户端连接
     *
     * @param key
     * @throws IOException
     */
    void handleAccept(SelectionKey key) throws IOException;

    void handleRead(SelectionKey key);

    void handleWrite(SelectionKey key) throws IOException;
}
