package com.study.deepinnetty.day1.demo.nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * @desc: buffer的使用
 */
public class NIOBuffer {

    public static void main(String[] args) {
        //Buffer 实际上是在维护一个数据 四个参数 mark(标记) position(读取的起始位置) limit(限制读取的位置) capacity(数组的容量)
        //拥有除了Boolean外所有基本类型的包装类的子类
        IntBuffer intBuffer = IntBuffer.allocate(5);
        intBuffer.put(1);
        intBuffer.put(2);
        intBuffer.put(3);
//        System.out.println(intBuffer.get() );
 //       System.out.println(intBuffer.get());
        //读写切换
        intBuffer.flip();
        //
        intBuffer.position(1);
        intBuffer.limit(2);
        intBuffer.capacity();
        while (intBuffer.hasRemaining() ) {
            System.out.println(intBuffer.get());
        }

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(5);
    }

}
