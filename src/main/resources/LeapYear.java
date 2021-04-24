package com.github.hollandjake.com3529.test;

public class LeapYear {

    public static boolean isLeapYear(int year) {

        boolean leap = false;

        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    leap = true;
                } else {
                    leap = false;
                }
            } else {
                leap = true;
            }
        } else {
            leap = false;
        }

        if (leap) {
            return true;
        } else {
            return false;
        }
    }
}