package com.winfred.training.netty.rpc.consumer.proxy;

import com.winfred.training.netty.rpc.protocol.InvokeProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class RpcProxy {
  public static <T> T create(Class<?> clazz) {
    MethodProxy methodProxy = new MethodProxy(clazz);
    T proxyInstance = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, methodProxy);
    return proxyInstance;
  }


  static class MethodProxy implements InvocationHandler {

    private Class<?> clazz;

    public MethodProxy(Class<?> clazz) {
      this.clazz = clazz;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
      if (object.getClass().equals(method.getDeclaringClass())) {
        return method.invoke(object, args);
      } else {
        return rpcInvoke(object, method, args);

      }
    }

    private Object rpcInvoke(Object object, Method method, Object[] args) {
      // 构造远程调用消息体
      InvokeProtocol invokeProtocol = new InvokeProtocol();

      invokeProtocol.setClassName(this.clazz.getName());
      invokeProtocol.setMethodName(method.getName());
      invokeProtocol.setParameterTypes(method.getParameterTypes());
      invokeProtocol.setParameterValues(args);

      final RpcProxyHandler rpcProxyHandler = new RpcProxyHandler();

      // 发起远程调用
      EventLoopGroup workGroup = new NioEventLoopGroup();

      Bootstrap bootstrap = new Bootstrap();

      bootstrap
          .group(workGroup)
          .channel(NioSocketChannel.class)
          .option(ChannelOption.TCP_NODELAY, true)
          .handler(new ChannelInitializer<SocketChannel>() {
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

              // 业务逻辑处理
              channelPipeline.addLast("rpc-handler-client", rpcProxyHandler);
            }
          });

      try {
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
        channelFuture.channel().writeAndFlush(invokeProtocol).sync();
        channelFuture.channel().closeFuture().sync();
      } catch (InterruptedException e) {
        log.error("", e);
      } finally {
        workGroup.shutdownGracefully();
      }

      return rpcProxyHandler.getResponse();
    }
  }
}
