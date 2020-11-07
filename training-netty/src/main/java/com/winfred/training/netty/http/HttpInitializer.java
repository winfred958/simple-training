package com.winfred.training.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http2.Http2ChannelDuplexHandler;
import io.netty.handler.ssl.SslContext;


public class HttpInitializer extends ChannelInitializer<SocketChannel> {

  private final SslContext sslCtx;

  public HttpInitializer(SslContext sslCtx) {
    this.sslCtx = sslCtx;
  }

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {

    ChannelPipeline pipeline = ch.pipeline();
    if (sslCtx != null) {
      pipeline.addLast(sslCtx.newHandler(ch.alloc()));
    }
    pipeline.addLast(new HttpServerCodec());
    pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
    pipeline.addLast(new MyHttpHandler());

  }
}