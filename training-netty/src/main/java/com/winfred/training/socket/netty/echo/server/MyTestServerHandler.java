package com.winfred.training.socket.netty.echo.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * channel handler pipline
 *
 * @author kevin
 */
@Slf4j
public class MyTestServerHandler extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast("echo-server", new EchoServerHandler());
    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        log.info("MyTestServerHandler channelActive");
//        super.channelActive(ctx);
//    }
}
