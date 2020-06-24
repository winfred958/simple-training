package com.winfred.training.netty.echo.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.LineEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * channel handler pipline
 *
 * @author kevin
 */
public class MyTestServerHandler extends ChannelInitializer<SocketChannel> {
  
  
  @Override
  protected void initChannel(SocketChannel socketChannel) throws Exception {
    ChannelPipeline channelPipeline = socketChannel.pipeline();
    channelPipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
    channelPipeline.addLast(new LineEncoder());
    channelPipeline.addLast("echo-server", new EchoServerHandler());
  }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        log.info("MyTestServerHandler channelActive");
//        super.channelActive(ctx);
//    }
}
