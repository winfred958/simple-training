package com.winfred.training.socket.aio.server;


public class TestAsyncServer {

    public static void main(String[] args) {
        AsyncServer asyncServer = new AsyncServer.Builder().setPort(19999).build();

        asyncServer.startServer();
    }
}
