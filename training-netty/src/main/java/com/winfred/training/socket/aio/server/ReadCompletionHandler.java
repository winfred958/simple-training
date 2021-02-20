package com.winfred.training.socket.aio.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

/**
 * @author kevin
 */
@Slf4j(topic = "ReadCompletionHandler")
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

  private AsynchronousSocketChannel asynchronousSocketChannel;

  private int sendCount = 0;

  public ReadCompletionHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
    this.asynchronousSocketChannel = asynchronousSocketChannel;
  }

  @Override
  public void completed(Integer result, ByteBuffer attachment) {
    attachment.flip();
    byte[] bytes = new byte[attachment.remaining()];
    attachment.get(bytes);
    // 客户端请求转码
    String requestStr = new String(bytes, StandardCharsets.UTF_8);

    String remoteHost = "";
    try {
      remoteHost = asynchronousSocketChannel.getRemoteAddress().toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
    log.info("server accept request {}-{}: {}", remoteHost, result, requestStr);
    String response = "";
    doWrite(response);
  }

  @Override
  public void failed(Throwable exc, ByteBuffer attachment) {

  }

  private void doWrite(String response) {
    if (StringUtils.isNotBlank(response)) {
      byte[] bytes = response.getBytes();
      ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
      writeBuffer.put(bytes);
      asynchronousSocketChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
        @Override
        public void completed(Integer result, ByteBuffer attachment) {
          if (attachment.hasRemaining() || sendCount < 3) {
            // 如果没有发送成功, 继续发送
            asynchronousSocketChannel.write(attachment, attachment, this);
            sendCount++;
          }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
          log.error("", exc);
          try {
            asynchronousSocketChannel.close();
          } catch (IOException e) {
            log.error("", e);
          }

        }
      });
    }
  }
}
