package com.winfred.training.socket.http;

import com.winfred.training.core.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.management.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

@Slf4j
public class SimpleHttpServerV2 {

  public static void main(String[] args) throws IOException {

    ServerSocket serverSocket = new ServerSocket(18088);

    log.info("{} start", SimpleHttpServerV2.class.getCanonicalName());
    while (true) {
      Socket socket = serverSocket.accept();

      socket.setSoTimeout(3000);
      ThreadPoolUtil
          .doExecutor(() -> {
            try {
              handler(socket);
            } catch (IOException e) {
              log.error("", e);
            }
          });

    }
  }

  private static void handler(Socket socket) throws IOException {
    try {
      // 模拟IO处理
      log.info("request accept: {}", socket.getLocalAddress().toString());
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
      printWriter.println("<html>");
      printWriter.println("<head>");
      printWriter.println("</head>");
      printWriter.println("<body>");

      printWriter.println("Hello, this is a http server");
      printWriter.println("<br/>");

      ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
      printWriter.println("classLoadingMXBean.getLoadedClassCount: " + classLoadingMXBean.getLoadedClassCount());
      printWriter.println("<br/>");
      printWriter.println("classLoadingMXBean.getUnloadedClassCount: " + classLoadingMXBean.getUnloadedClassCount());
      printWriter.println("<br/>");

      ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
      printWriter.println("threadMXBean.getThreadCount: " + threadMXBean.getThreadCount());
      printWriter.println("<br/>");

      MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
      MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
      MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

      printWriter.println("memoryMXBean.getHeapMemoryUsage: " + heapMemoryUsage);
      printWriter.println("<br/>");
      printWriter.println("memoryMXBean.getNonHeapMemoryUsage: " + nonHeapMemoryUsage);

      printWriter.println("</body>");
      printWriter.println("</html>");

      printWriter.flush();
    } finally {
      socket.close();
    }
  }
}
