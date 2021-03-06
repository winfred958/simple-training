package com.winfred.training.netty.echo.server;

import com.winfred.training.netty.echo.base.TestParameter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * com.winfred.training.socket.netty.server
 *
 * @author kevin
 * @since 2018/7/27 17:22
 */
public class MyTestServer {


  /**
   * boss thread
   */
  private static final NioEventLoopGroup parentEventLoop = new NioEventLoopGroup(8);
  /**
   * work thread
   */
  private static final NioEventLoopGroup childEventLoop = new NioEventLoopGroup(64);

  public void startServer() {
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    serverBootstrap.group(parentEventLoop, childEventLoop)
        .channel(NioServerSocketChannel.class)
        .option(ChannelOption.SO_BACKLOG, 100)
        .handler(new ChannelInitializer<NioServerSocketChannel>() {
          @Override
          protected void initChannel(NioServerSocketChannel channel) throws Exception {
            channel
                .pipeline()
                .addLast(new LoggingHandler(LogLevel.DEBUG));
          }
        })
        .childHandler(new MyTestServerHandler());

    try {
      ChannelFuture channelFuture = serverBootstrap.bind(TestParameter.SERVER_POT).sync();
      channelFuture.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      parentEventLoop.shutdownGracefully();
      childEventLoop.shutdownGracefully();
    }

  }

  public static void main(String[] args) {
    MyTestServer myTestServer = new MyTestServer();
    myTestServer.startServer();
  }
}

