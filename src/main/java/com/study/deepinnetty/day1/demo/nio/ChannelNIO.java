package com.study.deepinnetty.day1.demo.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 通道 Channel
 *      通道              流
 *      可读可写          读或者写
 *      可以异步读写      同步操作
 *
 *
 *  Channel是一个接口
 *  ++NetworkChannel
 *    ++SocketChannel  TCP读写
 *      **SocketChannelImpl
 *    ++ServerSocketChannel  TCP读写
 *      **ServerSocketChannelImpl
 *  ++FileChannel   文件数据读写
 *  ++DatagramChannel UDP通道
 *
 */
public class ChannelNIO {

    public static void main(String[] args) {
        //buffer.allocate 会使用hb[]数组; 如果申请allocateDirec则不会使用hb[],取值会直接从内存地址取
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //byte a = 'a';
        //byteBuffer.put(a);
        //byteBuffer.flip();
        //case1();
        //case2();
        case4();
    }

    /**
     * Channel实例1:本地文件写数据
     */

    public static void case1()  {
        String msg = "write file info is here";
        //创建文件输出流
        try(FileOutputStream fileOutputStream = new FileOutputStream("C:\\a.txt");FileChannel fileChannel = fileOutputStream.getChannel();) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
            byteBuffer.put(msg.getBytes(StandardCharsets.UTF_8) );
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Channel实例2:本地文件读取
     */

    public static void case2()  {
        String msg = "write file info is here";
        //创建文件输出流
        try(FileInputStream fileInputStream = new FileInputStream("C:\\a.txt"); FileChannel fileChannel = fileInputStream.getChannel();) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            fileChannel.read(byteBuffer);
            byteBuffer.flip();
            byteBuffer.asReadOnlyBuffer();
            /*byte[] b = new byte[1024];
            int index = 0;
            while (byteBuffer.hasRemaining() ) {
                b[index++] = byteBuffer.get();
            }
            System.out.println(new String(b));*/
            System.out.println(new String(byteBuffer.array() ) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
     * 使用一个buffer完成文件的读取和写入
     */
    public static void case3() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("C:\\b.txt"); final FileInputStream fileInputStream = new FileInputStream("C:\\a.txt");
            FileChannel fileChannel = fileInputStream.getChannel();FileChannel fileChannel2 = fileOutputStream.getChannel();){
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (fileChannel.read(byteBuffer) > 0) {
                byteBuffer.flip();
                fileChannel2.write(byteBuffer);
                fileOutputStream.flush();
                //需要清空buffer，不清空则读取不进去
                //byteBuffer.clear();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /*
     * channel复制文件
     */
    public static void case4() {
        File file = new File("C:\\Users\\Administrator\\Desktop\\testCase.jpg");
        File file1 = new File("C:\\Users\\Administrator\\Desktop\\1.jpg");
        if (!file1.exists() ) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(final FileInputStream fileInputStream = new FileInputStream(file);FileChannel fileChannel = fileInputStream.getChannel();
            FileOutputStream fileOutputStream = new FileOutputStream(file1);) {
            fileOutputStream.getChannel().transferFrom(fileChannel, 0 , file.length());
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
