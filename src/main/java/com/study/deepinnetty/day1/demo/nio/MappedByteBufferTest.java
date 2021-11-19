package com.study.deepinnetty.day1.demo.nio;

public class MappedByteBufferTest {

    public static void main(String[] args) {
        testCase1();
        /*try(RandomAccessFile randomAccessFile = new RandomAccessFile("C:\\a.txt","rw");FileChannel fileChannel =  randomAccessFile.getChannel();) {
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,12);
            mappedByteBuffer.put(0, (byte) '2');
            mappedByteBuffer.put(1, (byte) '2');
            mappedByteBuffer.put(2, (byte) '2');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public static void testCase1() {
        int[] a = new int[]{10,2,4,3,99,111,20,14};

        for (int i = 0,len=a.length; i < len; i++) {
            int max = a[i];
            int index = i;
            for (int j = i; j <len; j++) {
                if (max< a[j]) {
                    index= j;
                    max = a[j];
                }
            }
            a[index] = a[i];
            a[i] = max;
        }

        for (int i : a) {
            System.out.println(i);
        }
    }

}
