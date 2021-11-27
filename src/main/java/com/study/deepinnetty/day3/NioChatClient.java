package com.study.deepinnetty.day3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author xu
 * @desc 客户端连接
 */
public class NioChatClient {
    private SocketChannel socketChannel;
    private Selector selector;
    private static final int PORT=9311;
    public NioChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("101.35.192.236",PORT));
            socketChannel.register(selector, SelectionKey.OP_READ);

            if (!socketChannel.isConnected() ) {
                while (!socketChannel.finishConnect() ) {
                    System.out.println("wait server response");
                }
            }
            System.out.println("you have connect this server,you can send msg now,good lucky");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     */
    public void sendMsg(String msg) {
        ByteBuffer msgBuffer = ByteBuffer.wrap(msg.getBytes());
        try {
            socketChannel.write(msgBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 接收服务器端消息
     */
    public void receiveMsg() throws IOException {
        while (true) {
            if (selector.select() > 0) {
                final Set<SelectionKey> selectionKeys = selector.selectedKeys();
                final Iterator<SelectionKey> keys = selectionKeys.iterator();
                while (keys.hasNext() ) {
                    final SelectionKey key = keys.next();
                    if (key.isReadable()) {
                        final SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                        channel.read(byteBuffer);
                        System.out.println(new String (byteBuffer.array() ) );
                    }
                    keys.remove();
                }


            }
        }
    }

    public static void main(String[] args) {
        NioChatClient nioChatClient = new NioChatClient();
        new Thread() {
            @Override
            public void run() {
                try {
                    nioChatClient.receiveMsg();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext() ) {
            nioChatClient.sendMsg(scanner.next() );
        }
    }

}
