package com.winfred.training.socket.netty.echo.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    String message = "hello";


    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        ctx.writeAndFlush(message);
        log.info("request: {}", message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("response: {}", msg);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}