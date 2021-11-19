package com.study.deepinnetty.day1.demo.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServerDemo {


    public static void main(String[] args) throws IOException {

//创建一个线程池
        ExecutorService executors = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(8081);
        System.out.println("server loading");

        while (true) {
            //监听等待客户端连接
            //服务器启动后卡在此
            System.out.println("client haha");
            Socket socket = serverSocket.accept();
            executors.execute(new Runnable() {
                @Override
                //重写run方法
                public void run() {
                    System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName()+ ":client is conected");
                    handler(socket);
                }
            });

        }
    }

    public static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        StringBuilder msg = new StringBuilder();
        try (InputStream is= socket.getInputStream()){
            while (true) {
                System.out.println("kakkak");
                int rs = is.read(bytes);
                msg.append(new String(bytes));
                if (rs != -1) {
                    break;
                }
            }
            System.out.println("server accept msg:" + msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
