package com.winfred.training.socket.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;

/**
 * com.winfred.training.socket.netty.server
 *
 * @author kevin
 * @since 2018/7/27 17:22
 */
public class TestServer {

    NioEventLoopGroup parentEventLoop = new NioEventLoopGroup(1);
    NioEventLoopGroup childEventLoop = new NioEventLoopGroup(2);

    public void initServer() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(parentEventLoop, childEventLoop)
                .channel(NioServerSocketChannel.class).childOption(ChannelOption.TCP_NODELAY, true)
                .childAttr(AttributeKey.newInstance("childAttr"), "ttttttttt")
//                .handler(new ServerHandler())
                .childHandler(new ChannelHandler() {
                    @Override
                    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

                    }

                    @Override
                    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

                    }
                });

        try {
            ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

