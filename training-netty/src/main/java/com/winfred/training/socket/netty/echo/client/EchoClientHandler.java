package com.winfred.training.socket.netty.echo.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    Logger log = LoggerFactory.getLogger(EchoClientHandler.class);

    String message = "hello";

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        ctx.writeAndFlush(message);
        log.info("request: {}", message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("response: msg = [" + msg + "]");
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