package com.winfred.training.netty.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Slf4j
public class MyHttpHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    super.channelInactive(ctx);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
    String uri = fullHttpRequest.uri();

    // FIXME: uri, 增加路由

    handlerRequest(fullHttpRequest, ctx);

  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  @Override
  public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
    super.channelWritabilityChanged(ctx);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }


  private void handlerRequest(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
    FullHttpResponse response = null;
    try {
      String value = "hello, this a netty http server";
      log.info("request url: {}", ctx.channel().remoteAddress());

      response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
      response.headers().set("Content-Type", "application/json");
      response.headers().setInt("Content-Length", response.content().readableBytes());

    } catch (Exception e) {
      log.error("处理测试接口出错", e);
      response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
    } finally {
      if (fullRequest != null) {
        if (!HttpUtil.isKeepAlive(fullRequest)) {
          ctx
              .write(response)
              .addListener(ChannelFutureListener.CLOSE);
        } else {
          response.headers().set(CONNECTION, KEEP_ALIVE);
          ctx.write(response);
        }
      }
    }
  }
}
