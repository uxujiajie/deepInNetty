package com.study.deepinnetty.day3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * nio简易聊天室
 * @author xu
 * @date 2021/11/21
 */
public class NioChatGroup {
    public static void main(String[] args) {
        new NioChatGroup().readClientMsg();
    }


    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private static final int PORT=931112;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    //初始化
    public NioChatGroup() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            selector = Selector.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",PORT) );
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server init done. wait client user connect.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从客户端读取消息
     */
    public void readClientMsg()  {
        while (true ) {

            try {
                if (selector.select() > 0) {
                    final Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    final Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext() ) {
                        SelectionKey selectionKey= null;
                        SocketChannel socketChannel = null;
                        try {
                            selectionKey= iterator.next();
                            if (selectionKey.isAcceptable() ) {
                                ServerSocketChannel serverSocketChannel =  (ServerSocketChannel) selectionKey.channel();
                                SocketChannel socketChannel1 = serverSocketChannel.accept();
                                socketChannel1.configureBlocking(false);
                                socketChannel1.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                                System.out.println(socketChannel1.getRemoteAddress() + "is online" );

                            }

                            if (selectionKey.isReadable() ) {
                                socketChannel = (SocketChannel) selectionKey.channel();

                                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                socketChannel.read(byteBuffer);
                                System.out.println(socketChannel.getRemoteAddress() + " say:" + new String(byteBuffer.array()));
                                //向其余用户广播发言
                                broadcast(new String(byteBuffer.array()),socketChannel);
                            }

                        } catch (IOException e){
                            System.out.println(socketChannel.getRemoteAddress() + "is down");
                            if (selectionKey != null) {
                                selectionKey.cancel();
                            }
                            if (socketChannel !=null) {
                                socketChannel.close();
                            }
                        }
                    }
                    iterator.remove();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * @desc 向其余用户广播发言
     * @param msg 消息内容
     * @param socketChannel 发送用户的channel
     */
    private void broadcast(String msg, SocketChannel socketChannel) throws IOException {
        final Set<SelectionKey> keys = selector.keys();
        final Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()) {
            SelectionKey selectionKey = iterator.next();
            final SelectableChannel channel = (SelectableChannel) selectionKey.channel();
            if (channel instanceof SocketChannel && channel != socketChannel) {
                ByteBuffer byteBuffer = ByteBuffer.wrap((socketChannel.getRemoteAddress() + "say" +msg ).getBytes(StandardCharsets.UTF_8));
                System.out.println("server to" + ((SocketChannel) channel).getRemoteAddress() );
                ((SocketChannel) channel).write(byteBuffer);
            }
        }


    }


}
