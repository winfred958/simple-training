package com.winfred.training.socket.nio.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface MyServerProtocol {

    default void serverPrint(String str) {
        System.out.println(String.format("服务端 - 收到: %s", str));
    }

    /**
     * 处理客户端连接
     *
     * @param key
     * @throws IOException
     */
    void handleAccept(SelectionKey key) throws IOException;

    void handleRead(SelectionKey key) throws IOException;

    void handleWrite(SelectionKey key) throws IOException;
}
