package com.winfred.training.netty.rpc.registry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Registry {

    private int port;
    private int boosPoolSize;
    private int workerPoolSize;

    public Registry(Builder builder) {
        this.port = builder.port;
        this.boosPoolSize = builder.boosPoolSize;
        this.workerPoolSize = builder.workerPoolSize;
    }

    public void start() {
        // FIXME: 需要参数校验

        log.info("[ registry server ] starting at {} ......", port);
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // boos group 管理连接也就是 selector,默认cpu core * 2
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(boosPoolSize);

        // worker group 客户端的处理逻辑
        NioEventLoopGroup workGroup = new NioEventLoopGroup(workerPoolSize);

        serverBootstrap = serverBootstrap
                .group(boosGroup, workGroup)
                // 最大selector数
                .option(ChannelOption.SO_BACKLOG, 128)
                // 子线程池回收利用
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // netty, 所有业务逻辑归总到了一个队列中
                        // 这个队列包含一系列处理逻辑
                        // 封装成一个无锁的任务队列 Pipeline
                        ChannelPipeline channelPipeline = ch.pipeline();

                        // 数据解析 ↓
                        // 自定义协议的解码器
                        channelPipeline
                                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        // 自定义协议的编码器
                        channelPipeline
                                .addLast(new LengthFieldPrepender(4));

                        // 参数处理
                        channelPipeline.addLast("encoder", new ObjectEncoder());
                        channelPipeline.addLast("decode", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                        // 数据解析 ↑

                        log.info("client connected: {}", ch.remoteAddress());
                        // 业务逻辑处理
                        channelPipeline.addLast("rpc-handler", new RegistryHandler());

                    }
                });


        ChannelFuture channelFuture = null;
        try {
            // 正式绑定端口, 启动服务
            log.info("[ registry server ] started at {}", port);
            channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("", e);
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }


    }


    public static class Builder {
        private int port;
        private int boosPoolSize;
        private int workerPoolSize;

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setBoosPoolSize(int boosPoolSize) {
            this.boosPoolSize = boosPoolSize;
            return this;
        }

        public Builder setWorkerPoolSize(int workerPoolSize) {
            this.workerPoolSize = workerPoolSize;
            return this;
        }

        public Registry build() {
            return new Registry(this);
        }
    }


}
