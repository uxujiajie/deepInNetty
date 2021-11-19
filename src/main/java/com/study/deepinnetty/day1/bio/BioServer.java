package com.study.deepinnetty.day1.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {

    public static void main(String[] args) throws IOException {
        //线程池机制

        //创建线程池
        //如果有客户端连接，就创建一个线程
        ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);

        while (true) {
            final Socket socket = serverSocket.accept();

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //和客户端通讯
                        handler(socket);

                }
            });
        }


    }

    //编写一个handler
    public static void handler(Socket socket)  {
        StringBuilder sb = new StringBuilder();
        byte[] b = new byte[1024];
        try (InputStream inputStream = socket.getInputStream();){
            while (true) {
                if (inputStream.read(b) !=-1) {
                    sb.append(new String(b));
                    System.out.println(new String(b));
                }
                System.out.println(sb);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
