package com.github.hollandjake.com3529.test;

public class LongTest
{
    public static String smolNumber(long num)
    {
        if (num == (long) 5)
        {
            return "Yay we got da long";
        }
        else if (num > (long) 1000)
        {
            return "very long";
        }
        else
        {
            return "NOOOOO";
        }
    }
}