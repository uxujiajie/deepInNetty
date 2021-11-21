package com.study.deepinnetty.day2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    public static void main(String[] args) {

        try{
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",1997) );
            //设置为非阻塞
            serverSocketChannel.configureBlocking(false);
            //服务器管道注册到selector下,用以监听 Accept事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {

                if (selector.select(5000) == 0) {
                    System.out.println("5S内没有客户端连接");
                    continue;
                }
                //if selectedKey > 0,means channel has accept event
                final Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext() ) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable() ) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                    }
                    if (selectionKey.isReadable() ) {
                        SocketChannel selectableChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                        //byteBuffer.flip();
                        selectableChannel.read(byteBuffer);
                        System.out.println("server have done client msg:" + new String(byteBuffer.array() ));
                    }
                    iterator.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
