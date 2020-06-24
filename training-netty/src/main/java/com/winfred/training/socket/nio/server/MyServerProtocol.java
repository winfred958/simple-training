package com.winfred.training.socket.nio.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;


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
