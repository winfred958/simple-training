package com.winfred.training.socket.bio;

import com.winfred.training.core.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO (Bloking IO)
 * <p>
 * 服务端线程从建立连接开始阻塞.
 * 单通道:
 * Socket.InputStream.read 客户端未发送或未发送完毕, 服务端线程阻塞
 * Socket.OutputStream.write 客户端未接收, 服务端线程阻塞
 */
@Slf4j
public class BIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        ThreadPoolUtil.doExecutor(() -> {
            while (true) {
                // 1. 阻塞获取新连接
                try {
                    // FIXME: block connect mode, 服务端开始阻塞
                    Socket socket = serverSocket.accept();
                    // 2. 每一个新连接都启动一个线程处理
                    ThreadPoolUtil.doExecutor(() -> {
                        handler(socket);
                    });
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        });
    }

    /**
     * Socket.InputStream.read 客户端未发送或未发送完毕, 服务端线程阻塞
     * 或
     * Socket.OutputStream.write 客户端未接收, 服务端线程阻塞
     *
     * @param socket
     */
    private static void handler(Socket socket) {
        try (
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
        ) {
            // 读取
            byte[] data = new byte[4096];
            int len;
            while ((len = inputStream.read(data)) != -1) {
                String msg = new String(data, 0, len);
                log.info("accept: {}", msg);
            }

//            // 回应
//            String hi = "ok";
//            outputStream.write(hi.getBytes());
//            outputStream.flush();
        } catch (IOException e) {
            log.error("socket close ", e);
        }
        log.info("1 个子线程消亡 {}", Thread.currentThread().getName());
    }
}
