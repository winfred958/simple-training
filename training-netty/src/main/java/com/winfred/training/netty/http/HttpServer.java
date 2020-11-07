package com.winfred.training.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class HttpServer {
  private EventLoopGroup boosGroup;
  private EventLoopGroup workGroup;

  private ServerBootstrap serverBootstrap;

  private InetSocketAddress httpAddress;

  private int port;

  private boolean ssl;

  public HttpServer(int port, boolean ssl) {
    this.port = port;
    this.ssl = ssl;
  }

  public void run() throws Exception {
    final SslContext sslContext;
    if (ssl) {
      SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
      sslContext = SslContext.newServerContext(selfSignedCertificate.certificate(), selfSignedCertificate.privateKey());
    } else {
      sslContext = null;
    }
    boosGroup = new NioEventLoopGroup(4);
    workGroup = new NioEventLoopGroup(1024);
    try {

      serverBootstrap = new ServerBootstrap();
      serverBootstrap
              .option(ChannelOption.SO_BACKLOG, 128)
              .option(ChannelOption.TCP_NODELAY, true)
              .option(ChannelOption.SO_KEEPALIVE, true)
              .option(ChannelOption.SO_REUSEADDR, true)
              .option(ChannelOption.SO_RCVBUF, 32 * 1024)
              .option(ChannelOption.SO_SNDBUF, 32 * 1024)
              .option(EpollChannelOption.SO_REUSEPORT, true)
              .childOption(ChannelOption.SO_KEEPALIVE, true);

      serverBootstrap
              .group(boosGroup, workGroup)
              .channel(NioServerSocketChannel.class)
              .handler(new LoggingHandler(LogLevel.DEBUG))
              // FIXME: add http handler
              .childHandler(new HttpInitializer(sslContext))
      ;

      Channel channel = serverBootstrap
              .bind(port)
              .sync()
              .channel();

      log.info("Netty http server 0.0.0.0:{}", port);

      channel.closeFuture().sync();
    } finally {
      workGroup.shutdownGracefully();
      boosGroup.shutdownGracefully();
    }


  }


}
