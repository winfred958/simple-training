package com.winfred.training.socket.netty.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;

/**
 * echo channel handler
 *
 * @author kevin
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive: " + ctx.channel().id());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive: " + ctx.channel().id());
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.remoteAddress();
        String hostAddress = inetSocketAddress.getAddress().getHostAddress();

        ChannelId channelId = channel.id();

//        new PooledUnsafeDirectByteBuf();


        if (msg instanceof ByteBuf) {
            ByteBuf requestByteBuf = (ByteBuf) msg;

            /**
             * 防止读完被清空
             */
            ByteBuf tmpBuf = requestByteBuf.copy();
            int len = tmpBuf.readableBytes();
            byte[] b = new byte[len];
            tmpBuf.readBytes(b);

            String str = new String(b);
            System.out.println("channelRead: " + channelId + " | " + hostAddress + " | " + str);
            byte[] responseBytes = new String(hostAddress + " | " + str).getBytes();
            // 修改数据

        } else {
            System.out.println("channelRead: " + channelId + " | " + hostAddress + " | " + msg);
        }

        // 回写收到的消息
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
