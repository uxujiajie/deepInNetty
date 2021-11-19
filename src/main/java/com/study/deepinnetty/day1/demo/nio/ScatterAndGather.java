package com.study.deepinnetty.day1.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * buffer的分散与聚合
 * scattering，写入buffer数组时，依次写入[分散]
 */
public class ScatterAndGather {

    public static void main(String[] args) {
        try(ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
        ) {
            SocketAddress socketAddress = new InetSocketAddress(8001);
            serverSocketChannel.socket().bind(socketAddress);

            ByteBuffer[] byteBuffers = new ByteBuffer[10];
            for (int i = 0,size=byteBuffers.length; i<size; i++) {
                byteBuffers[i] = ByteBuffer.allocateDirect(12);
            }
            //Arrays.stream(byteBuffers).forEach(buffer -> buffer= ByteBuffer.allocateDirect(12));
            SocketChannel socketChannel = serverSocketChannel.accept();
            int msgLength = 120;
            //循环读取
            while (true) {
                int byteRead = 0;
                while (byteRead < msgLength) {
                    final long read = socketChannel.read(byteBuffers);
                    byteRead+=read;
                    System.out.println(byteRead + " byte");
                    //输出
                    Arrays.stream(byteBuffers).map(buffer -> buffer.position()+"-"+buffer.limit() ).forEach(System.out :: println);
                }

                //将所有的buffer进行反转
                Arrays.stream(byteBuffers).forEach(buffer -> buffer.flip() );
                //将数据读出显示到客户端
                long byteWrite = 0;
                while (byteWrite < msgLength) {
                    long write = socketChannel.write(byteBuffers);
                    byteWrite+=write;
                }

                Arrays.stream(byteBuffers).forEach(buffer -> buffer.clear() );
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
