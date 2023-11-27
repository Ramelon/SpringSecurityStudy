package com.ramelon.security;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/22 8:39
 */
public class test {
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            System.out.println((Math.random() * 9) * 100000);
        }
    }
}