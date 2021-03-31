package com.github.hollandjake.test;

import com.github.hollandjake.com3529.generation.CoverageReport;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;

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

        //side1 > side2
        if (coverage.cover(0, side1 > side2)) {
            int temp = side1;
            side1 = side2;
            side2 = temp;
        }
        //side1 > side3
        if (coverage.cover(1, side1 > side3)) {
            int temp = side1;
            side1 = side3;
            side3 = temp;
        }
        //side2 > side3
        if (coverage.cover(2, side2 > side3)) {
            int temp = side2;
            side2 = side3;
            side3 = temp;
        }

        //side1 + side2 <= side3
        if (coverage.cover(3, side1 + side2 <= side3)) {
            type = Type.INVALID;
        } else {
            type = Type.SCALENE;
            //side1 == side2
            if (coverage.cover(4, side1 == side2)) {
                //side2 == side3
                if (coverage.cover(5, side2 == side3)) {
                    type = Type.EQUILATERAL;
                }
            } else {
                //side2 == side3
                if (coverage.cover(6, side2 == side3)) {
                    type = Type.ISOSCELES;
                }
            }
        }
        return type;
    }
}
