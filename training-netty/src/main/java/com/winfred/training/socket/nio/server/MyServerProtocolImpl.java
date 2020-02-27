package com.winfred.training.socket.nio.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MyServerProtocolImpl implements MyServerProtocol {

    private int bufferSize;

    public MyServerProtocolImpl(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public void handleConnect(SelectionKey key)  {
        // 获取事件句柄对应的 SocketChannel
        SocketChannel channel = (SocketChannel) key.channel();

        // 真正的完成 socket 连接
        try {
            channel.finishConnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 打印连接信息
        InetSocketAddress remote = (InetSocketAddress) channel.socket().getRemoteSocketAddress();
        String host = remote.getHostName();
        int port = remote.getPort();
        log.info("访问地址: {}:{} 连接成功!", host, port);
    }


    @Override
    public void handleAccept(SelectionKey key) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        // 建立 socket channel (双工)
        SocketChannel socketChannel = null;
        try {
            socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            // 注册, 监听 read event
            socketChannel.register(key.selector(), SelectionKey.OP_READ);
        } catch (IOException e) {
            log.error("{} [handleAccept]", this.getClass().getName(), e);
        }
    }

    @Override
    public void handleRead(SelectionKey key) {

        try {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
            byteBuffer.clear();
            // 开始读取数据
            int length = 0;
            length = socketChannel.read(byteBuffer);

            if (length == -1) {
                return;
            }
            // 将缓冲区数据置为传出状态
            byteBuffer.flip();
            // 将字节转化为为UTF-8的字符串

            String receivedString = StandardCharsets.UTF_8.newDecoder().decode(byteBuffer).toString();

            SocketAddress remoteAddress = socketChannel.getRemoteAddress();
            log.info("{} >> {}", remoteAddress.toString(), receivedString);

            if (StringUtils.isNotBlank(receivedString)) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                String responseStr = String.format("已经收到: %s", receivedString);
                buffer.put(responseStr.getBytes(StandardCharsets.UTF_8));
                buffer.flip();
                socketChannel.write(buffer);
            }
        } catch (IOException e) {
            log.error("远程连接关闭.", e);
        }
    }

    @Override
    public void handleWrite(SelectionKey key, String message) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        InetSocketAddress remote = (InetSocketAddress) channel.socket().getRemoteSocketAddress();
        String host = remote.getHostName();

        // 向 SocketChannel 写入事件
        channel.write(Charset.defaultCharset().encode(message));

        // 修改 SocketChannel 所关心的事件
        key.interestOps(SelectionKey.OP_READ);

    }
}
