package com.winfred.training.socket.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.SimpleFormatter;

public class BIOClient {
    public static void main(String[] args) {
        String ip = "127.0.0.1";
        int port = 6666;

        new Thread(() -> {
            try {
                Socket socket = new Socket(ip, port);
                while (true) {
                    OutputStream outputStream = socket.getOutputStream();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Calendar calendar = Calendar.getInstance();
                    String msg = simpleDateFormat.format(calendar.getTime()) + ": hello";
                    outputStream.write(msg.getBytes());
                    outputStream.flush();
                    System.out.println("send -> : " + msg);
                    Thread.sleep(2000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
