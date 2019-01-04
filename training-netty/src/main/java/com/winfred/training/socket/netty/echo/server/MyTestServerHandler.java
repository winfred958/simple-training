package com.winfred.training.socket.netty.echo.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * channel handler pipline
 *
 * @author kevin
 */
public class MyTestServerHandler extends ChannelInitializer<SocketChannel> {

    Logger log = LoggerFactory.getLogger(MyTestServerHandler.class);

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast("echo", new EchoServerHandler());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("MyTestServerHandler channelActive" );
        super.channelActive(ctx);
    }
}
