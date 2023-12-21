package com.ramelon.user;

/**
 * @description:
 * @author: xzy
 * @time: 2023/12/13 16:13
 */
public class test {
    public static void main(String[] args) {
        System.out.println(getNum(32));
    }

    public static int getNum(int num){
        num = num * 2 - 2;
        num |= num >> 1;
        num |= num >> 2;
        num |= num >> 4;
        num |= num >> 8;
        num |= num >> 16;
        return num - (num >>> 1);
    }
}
