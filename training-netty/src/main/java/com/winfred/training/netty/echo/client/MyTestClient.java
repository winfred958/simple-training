package com.winfred.training.netty.echo.client;

import com.winfred.training.netty.echo.base.TestParameter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyTestClient {


    static EventLoopGroup eventLoopGroup = new NioEventLoopGroup();


    public static void main(String[] args) throws IOException {


        Bootstrap bootstrap = new Bootstrap();

        bootstrap
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new MyTestClientHandeler());
        try {
            ChannelFuture channelFuture = bootstrap.connect(TestParameter.SEVER_IP, TestParameter.SERVER_POT).sync();
//            channelFuture.channel().closeFuture().sync();
            Channel channel = channelFuture.channel();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                String line = in.readLine();
                if (line == null || "quit".equalsIgnoreCase(line)) {
                    break;
                }
                if (line.length() < 1) {
                    continue;
                }

                channel.writeAndFlush(line);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }


}
