package com.github.hollandjake.com3529.test;

public class HackThePentagon {

    public static String launchNukes(String password, Long code1, Short code2, boolean fireNukes) {
        boolean check1 = false;
        boolean check2 = false;

        if (password == "123") {
            for (Long i = 0L; i < 100L; i++) {
                if (code1 == i) {
                    check1 = true;
                }
            }
            for (Short i = (short)0; i < (short)100; i++) {
                if (code2 == i) {
                    check2 = true;
                }
            }
            if (check1 == true && check2 == true && fireNukes) {
                return "Nukes have been launched!";
            } else {
                return "The word is safe for now";
            }
        } else {
            return "Incorrect password";
        }
    }
}