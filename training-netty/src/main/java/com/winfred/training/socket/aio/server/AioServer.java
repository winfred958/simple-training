package com.winfred.training.socket.aio.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author kevin
 */
@Slf4j(topic = "AsyncServer")
public class AioServer {
  
  private int port;
  
  /**
   * 使用栅栏, 控制异步, 不然主线程会直接退出
   */
  CountDownLatch countDownLatch = new CountDownLatch(1);
  
  AsynchronousServerSocketChannel asynchronousServerSocketChannel;
  
  public AioServer(Builder builder) {
    this.port = builder.port;
  }
  
  public void startServer() {
    try {
      asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
      asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
      
      log.info("server starting at port {}", port);
      
      doAccept();
      try {
        // 阻塞主进程
        countDownLatch.await();
      } catch (InterruptedException e) {
        log.error("CountDownLatch InterruptedException: ", e);
      }
    } catch (IOException e) {
      log.error("", e);
    }
  }
  
  public void stop() {
    countDownLatch.countDown();
  }
  
  /**
   * 处理客户端连接
   */
  private void doAccept() {
    asynchronousServerSocketChannel
            .accept(this, new CompletionHandler<AsynchronousSocketChannel, AioServer>() {
              @Override
              public void completed(AsynchronousSocketChannel socketChannel, AioServer server) {
                server.asynchronousServerSocketChannel.accept(server, this);
                
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                socketChannel.read(byteBuffer, byteBuffer, new ReadCompletionHandler(socketChannel));
              }
              
              @Override
              public void failed(Throwable exc, AioServer asyncServer) {
                log.error("[ Asnc Server failed ]", exc);
                asyncServer.stop();
              }
            });
  }
  
  
  public static class Builder {
    private int port;
    
    public Builder setPort(int port) {
      this.port = port;
      return this;
    }
    
    public AioServer build() {
      return new AioServer(this);
    }
  }
}
