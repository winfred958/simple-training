package com.winfred.training.socket.aio.server;


import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "TestAsyncServer")
public class AioServerTest {
  
  public static void main(String[] args) {
    AioServer asyncServer = new AioServer.Builder()
            .setPort(19999)
            .build();
    
    asyncServer.startServer();
    
    asyncServer.stop();
  }
}
