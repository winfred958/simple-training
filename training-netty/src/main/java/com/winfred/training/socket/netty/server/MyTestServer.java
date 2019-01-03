package com.winfred.training.socket.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;

/**
 * com.winfred.training.socket.netty.server
 *
 * @author kevin
 * @since 2018/7/27 17:22
 */
public class MyTestServer {

    private int port = 8090;

    public MyTestServer(int port) {
        this.port = port;
    }

    /**
     * boss thread
     */
    private static final NioEventLoopGroup parentEventLoop = new NioEventLoopGroup(1);
    /**
     * work thread
     */
    private static final NioEventLoopGroup childEventLoop = new NioEventLoopGroup(2);

    public void startServer() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(parentEventLoop, childEventLoop)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childAttr(AttributeKey.newInstance("childAttr"), "ttttttttt")
                .childHandler(new MyTestServerHandler());

        try {
            ChannelFuture channelFuture = serverBootstrap.bind(this.port).sync();
            channelFuture.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        MyTestServer myTestServer = new MyTestServer(8080);
        myTestServer.startServer();
    }
}

