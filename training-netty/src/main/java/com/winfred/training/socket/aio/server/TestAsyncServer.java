package com.winfred.training.socket.aio.server;


import org.junit.Before;
import org.junit.Test;

public class TestAsyncServer {

    AsyncServer asyncServer;

    @Before
    public void before() {
        this.asyncServer = new AsyncServer(6666);
    }

    @Test
    public void testAioServer() {
        asyncServer.startServer();

    }
}
