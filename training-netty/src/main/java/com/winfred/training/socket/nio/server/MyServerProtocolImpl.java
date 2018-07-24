package com.winfred.training.socket.nio.server;

import org.apache.commons.lang3.StringUtils;

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
    public void handleConnect(SelectionKey key) throws IOException {
        // 获取事件句柄对应的 SocketChannel
        SocketChannel channel = (SocketChannel) key.channel();

        // 真正的完成 socket 连接
        channel.finishConnect();

        // 打印连接信息
        InetSocketAddress remote = (InetSocketAddress) channel.socket().getRemoteSocketAddress();
        String host = remote.getHostName();
        int port = remote.getPort();
        System.out.println(String.format("访问地址: %s:%s 连接成功!", host, port));
    }


    @Override
    public void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(), SelectionKey.OP_READ);
    }

    @Override
    public void handleRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);

        // 开始读取数据
        socketChannel.read(byteBuffer);
        // 将缓冲区数据置为传出状态
        byteBuffer.flip();
        // 将字节转化为为UTF-8的字符串
        String receivedString = Charset.defaultCharset().newDecoder().decode(byteBuffer).toString();

        this.serverPrint(receivedString);

        if (StringUtils.isBlank(receivedString)) {
            key.cancel();
            socketChannel.close();
            return;
        }
    }

    @Override
    public void handleWrite(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        InetSocketAddress remote = (InetSocketAddress) channel.socket().getRemoteSocketAddress();
        String host = remote.getHostName();

        // 向 SocketChannel 写入事件
        channel.write(Charset.defaultCharset().encode("aaaaaaaaaaaaaaaaaaa"));

        // 修改 SocketChannel 所关心的事件
        key.interestOps(SelectionKey.OP_READ);

    }
}
