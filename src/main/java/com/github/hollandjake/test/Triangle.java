package com.github.hollandjake.test;

import com.github.hollandjake.com3529.generation.CoverageReport;

public class Triangle {

    public enum Type
    {
        INVALID,
        SCALENE,
        EQUILATERAL,
        ISOSCELES;
    }

    public static Type classify(int side1, int side2, int side3, CoverageReport coverage) {
        Type type;
        if (coverage.cover(0, side1 > side2)) {
            int temp = side1;
            side1 = side2;
            side2 = temp;
        }
        if (coverage.cover(1, side1 > side3)) {
            int temp = side1;
            side1 = side3;
            side3 = temp;
        }
        if (coverage.cover(2, side2 > side3)) {
            int temp = side2;
            side2 = side3;
            side3 = temp;
        }
        if (coverage.cover(3, side1 + side2 <= side3)) {
            type = Type.INVALID;
        } else {
            type = Type.SCALENE;
            if (coverage.cover(4, side1 == side2)) {
                if (coverage.cover(5, side2 == side3)) {
                    type = Type.EQUILATERAL;
                }
            } else {
                if (coverage.cover(6, side2 == side3)) {
                    type = Type.ISOSCELES;
                }
            }
        }
        return type;
    }
}
