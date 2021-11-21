package com.study.deepinnetty.day2;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NioClient {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1",1997));
            if (!socketChannel.isConnected() ) {
                while (!socketChannel.finishConnect() ) {
                    System.out.println("等待连接服务器");
                }
            }
            String str = "hi this is client";
            ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes() );
            socketChannel.write(byteBuffer);
            System.out.println("发送完成");
            //System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
