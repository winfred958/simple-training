package com.winfred.training.netty.time.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {
  
  private int port = 5555;
  
  public void bind(int port) {
    EventLoopGroup parentGroup = new NioEventLoopGroup();
    EventLoopGroup childGroup = new NioEventLoopGroup();
    
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    serverBootstrap
            .group(parentGroup, childGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 1024)
            .childHandler(new ChannelInitializer<SocketChannel>() {
              @Override
              protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new TimeServerHandler());
              }
            })
    ;
    
    try {
      
      // 绑定端口
      ChannelFuture future = serverBootstrap
              .bind(port)
              .sync();
      
      // 等待服务器监听端口关闭
      future.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      // 优雅退出, 释放线程池资源
      parentGroup.shutdownGracefully();
      childGroup.shutdownGracefully();
      
    }
    
  }
  
  class ChildChannelhandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
      ch.pipeline().addLast(new TimeServerHandler());
    }
  }
}
