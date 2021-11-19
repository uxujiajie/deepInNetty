package com.study.deepinnetty.leetcode;

public class LeetCode {
    public static void main(String[] args) {
        System.out.println(Math.floor(Math.sqrt(9)));
        System.out.println(bulbSwitch(9));
    }
    public static int bulbSwitch(int n) {
        boolean[] bi = new boolean[n];
        for (int i =2;i<=n;++i) {
            //System.out.println(i);
            for(int j = 0; j < n; ++j) {
                //if (i==3) {
                   // System.out.print(bi[j] + " ");
               // }
                if((j+1)%i == 0) {
                    bi[j]=!bi[j];
                }
               /* if (i==3) {
                    System.out.println(bi[j] + " ");
                }*/
            }
        }
        int temp = 0;

        for(int i = 0;i <n;i++) {
            //System.out.print(bi[i]+" ");
            if(bi[i] == false) {
                temp++;
            }
        }
        //System.out.println();
        return temp;
    }
}
