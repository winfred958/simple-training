package com.winfred.training.socket.netty.echo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

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
                .option(ChannelOption.SO_BACKLOG, 100)
                .childHandler(new MyTestServerHandler());

        try {
            ChannelFuture channelFuture = serverBootstrap.bind(this.port).sync();
//            channelFuture.await();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            parentEventLoop.shutdownGracefully();
            childEventLoop.shutdownGracefully();

        }

    }

    public static void main(String[] args) {
        MyTestServer myTestServer = new MyTestServer(8080);
        myTestServer.startServer();
    }
}

