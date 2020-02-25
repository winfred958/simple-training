package com.winfred.training.socket.bio;

import com.winfred.training.core.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Slf4j
public class BIOClient {
    public static void main(String[] args) {
        String ip = "127.0.0.1";
        int port = 6666;
        ThreadPoolUtil.doExecutor(() -> {
            try (
                    Socket socket = new Socket(ip, port);
                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();
            ) {
                while (true) {

                    // 写
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Calendar calendar = Calendar.getInstance();
                    String msg = simpleDateFormat.format(calendar.getTime()) + ": hello";
                    outputStream.write(msg.getBytes());
                    outputStream.flush();
                    log.info("send   : {}", msg);

//                    // 读
//                    byte[] data = new byte[4096];
//                    int len;
//                    while ((len = inputStream.read(data)) != -1) {
//                        String response = new String(data, 0, len);
//                        log.info("get: {}", response);
//                    }
//                    inputStream.reset();

                    Thread.sleep(2000);
                }
            } catch (IOException e) {
                log.error("socket close ", e);
            } catch (InterruptedException e) {
                log.error("sleep InterruptedException ", e);
            }
        });
    }
}
