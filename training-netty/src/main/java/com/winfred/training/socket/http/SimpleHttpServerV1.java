package com.winfred.training.socket.http;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

@Slf4j
public class SimpleHttpServerV1 {

  @SneakyThrows
  public static void main(String[] args) {

    ServerSocket serverSocket = new ServerSocket(18088);

    log.info("SimpleHttpServer start");
    while (true) {
      try (Socket socket = serverSocket.accept();) {

        socket.setSoTimeout(3000);
        handler(socket);
      } catch (IOException e) {
        log.error("", e);
      }

    }
  }

  private static void handler(Socket socket) throws IOException {
    try {
      // 模拟IO处理
      log.info("request accept");
      Thread.sleep(20);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    String uuid = UUID.randomUUID().toString();


    try (
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), false);
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    ) {

      String str = null;
      while ((str = bufferedReader.readLine()) != null) {
        log.info("{} | {}", uuid, str);
        break;
      }

      printWriter.println("HTTP/1.1 200 OK");
      printWriter.println("Content-Type:text/html;charset=utf-8");
      printWriter.println();
      printWriter.println("Hello, this is a http server");

      ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
      printWriter.println("classLoadingMXBean.getLoadedClassCount: " + classLoadingMXBean.getLoadedClassCount());

      printWriter.flush();
    }
  }
}
