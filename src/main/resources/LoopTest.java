package com.github.hollandjake.com3529.test;

public class LoopTest
{
    public static boolean test(int input) {
        for (int i = 0; i < 100; i++) {
            if (i == input) {
                return true;
            }
        }
        return false;
    }
}
