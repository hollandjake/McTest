package com.github.hollandjake.com3529.test;

public class GoodProgram {

    public static String isMcTestAGoodProgram(String author, boolean programFinished, boolean documented, int commits) {
        if (author == "Jake" || author == "Tom") {
            if (programFinished == true) {
                if (documented == true) {
                    if (commits > 50) {
                        return "It's a good program";
                    } else {
                        return "Better get more commits";
                    }
                } else {
                    return "Add your documentation";
                }
            } else {
                return "It needs finishing";
            }
        } else {
            return "Wrong authors";
        }
    }
}