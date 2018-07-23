package com.winfred.training.socket.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class MyServerProtocolImpl implements MyServerProtocol {

    private int bufferSize;

    public MyServerProtocolImpl(int bufferSize) {
        bufferSize = bufferSize;
    }

    @Override
    public void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
    }

    @Override
    public void handleRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
        byteBuffer.clear();
        int len = socketChannel.read(byteBuffer);

        if (len < 0) {
            socketChannel.finishConnect();
            return;
        }
        ;

        // 开始读取数据
        // 将缓冲区数据置为传出状态
        byteBuffer.flip();
        // 将字节转化为为UTF-8的字符串
        String receivedString = Charset.defaultCharset().newDecoder().decode(byteBuffer).toString();
        this.serverPrint(receivedString);
        // 准备并返回响应数据
        String sendStr = String.format("你好%s, 收到: %s", socketChannel.getRemoteAddress(), receivedString);
        ByteBuffer buffer = ByteBuffer.wrap(sendStr.getBytes(Charset.defaultCharset()));

        socketChannel.write(buffer);

        // 设置为下一次读取或是写入做准备
        key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    @Override
    public void handleWrite(SelectionKey key) throws IOException {
        // FIXME: nothing
        SocketChannel socketChannel = (SocketChannel) key.channel();

        key.attachment();


    }
}
