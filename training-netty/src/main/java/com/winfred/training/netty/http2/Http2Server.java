package com.winfred.training.netty.http2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;

import java.net.InetSocketAddress;

public class Http2Server {
  private EventLoopGroup boosGroup;
  private EventLoopGroup workGroup;
  
  private ServerBootstrap httpServer;
  private ServerBootstrap httpsServer;
  
  private InetSocketAddress httpAddress;
  private InetSocketAddress httpsAddress;
  
  public static void main(String[] args) {
    
  }
}
