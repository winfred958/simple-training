package com.winfred.training.socket.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);

        new Thread(() -> {
            while (true) {
                // 1. 阻塞获取新连接
                try {
                    Socket socket = serverSocket.accept();
                    // 2. 每一个新连接都启动一个线程处理
                    new Thread(() -> {
                        try {
                            byte[] data = new byte[4096];
                            InputStream inputStream = socket.getInputStream();
                            while (true) {
                                int len;
                                while ((len = inputStream.read(data)) != -1) {
                                    System.out.println(new String(data, 0, len));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
