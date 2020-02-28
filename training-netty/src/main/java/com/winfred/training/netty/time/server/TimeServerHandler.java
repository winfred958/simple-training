package com.winfred.training.netty.time.server;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 建立连接时
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * To send a new message, we need to allocate a new buffer which will contain the message. We are going to write a 32-bit integer,
         * and therefore we need a ByteBuf whose capacity is at least 4 bytes. Get the current ByteBufAllocator via ChannelHandlerContext.alloc() and allocate a new buffer.
         */
        final ByteBuf byteBuf = ctx.alloc().buffer(4);
        byteBuf.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        /**
         * 写入并刷新
         */
        final ChannelFuture channelFuture = ctx.writeAndFlush(byteBuf);
        /**
         *
         *
         * 可以用预定义侦听器代替: addListener(ChannelFutureListener.CLOSE)
         */
        channelFuture
                .addListener(new ChannelFutureListener() {

                    @Override
                    public void operationComplete(ChannelFuture future) {
                        assert channelFuture == future;
                        ctx.close();
                    }
                });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
