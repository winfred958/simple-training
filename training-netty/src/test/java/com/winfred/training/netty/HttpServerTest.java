package com.winfred.training.netty;

import com.winfred.training.netty.http.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class HttpServerTest {

  @Test
  public void startHttpServer() {
    HttpServer server = new HttpServer(18808, false);
    try {
      server.run();
    } catch (Exception e) {
      log.error("", e);
    }
  }
}
