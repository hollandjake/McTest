package com.github.hollandjake.com3529.test;

public class IsAlphabet {

    public static boolean isInAlphabet(char c) {
        if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            return true;
        } else {
            return false;
        }
    }
}