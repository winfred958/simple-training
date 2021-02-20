package com.winfred.training.netty.rpc.consumer.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcProxyHandler extends ChannelInboundHandlerAdapter {

  private Object response;

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    response = msg;

  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    log.error("", cause);
  }

  public Object getResponse() {
    return response;
  }
}
