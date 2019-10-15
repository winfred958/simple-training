package com.winfred.training.socket.netty.echo.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive: " + ctx.name());
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String name = ctx.name();
        String asLongText = ctx.channel().id().asLongText();

        if (msg instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
            /**
             * 防止读完被清空
             */
            ByteBuf tmpBuf = byteBuf.copy();
            int len = tmpBuf.readableBytes();
            byte[] b = new byte[len];
            tmpBuf.readBytes(b);

            String str = new String(b);
            System.out.println("channelRead: " + asLongText + " | " + name + " | " + str);
        } else {
            System.out.println("channelRead: " + asLongText + " | " + name + " | " + msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("channelReadComplete: " + ctx.name());
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }


}