package com.winfred.training.socket.nio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;


public interface MyServerProtocol {

    void handleConnect(SelectionKey key);

    /**
     * 处理客户端连接
     *
     * @param key
     * @throws IOException
     */
    void handleAccept(SelectionKey key);

    void handleRead(SelectionKey key);

    void handleWrite(SelectionKey key, String message) throws IOException;
}
