package com.winfred.training.socket.bio;

import com.winfred.training.core.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
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
            try {
                Socket socket = new Socket(ip, port);
                while (true) {
                    OutputStream outputStream = socket.getOutputStream();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Calendar calendar = Calendar.getInstance();
                    String msg = simpleDateFormat.format(calendar.getTime()) + ": hello";
                    outputStream.write(msg.getBytes());
                    outputStream.flush();
                    log.info("send   : {}", msg);
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
