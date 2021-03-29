package com.github.hollandjake.test;

import com.github.hollandjake.com3529.genetics.Gym;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;

public class Triangle {

    public enum Type
    {
        INVALID,
        SCALENE,
        EQUILATERAL,
        ISOSCELES;
    }

    public static Type classify(int side1, int side2, int side3, Gym gym) {
        Type type;

        //side1 > side2
        if (gym.workout(0, new BinaryExpr(new StringLiteralExpr(String.valueOf(side1)), new StringLiteralExpr(String.valueOf(side2)), BinaryExpr.Operator.GREATER))) {
            int temp = side1;
            side1 = side2;
            side2 = temp;
        }
        //side1 > side3
        if (gym.workout(1, new BinaryExpr(new StringLiteralExpr(String.valueOf(side1)), new StringLiteralExpr(String.valueOf(side3)), BinaryExpr.Operator.GREATER))) {
            int temp = side1;
            side1 = side3;
            side3 = temp;
        }
        //side2 > side3
        if (gym.workout(2, new BinaryExpr(new StringLiteralExpr(String.valueOf(side2)), new StringLiteralExpr(String.valueOf(side3)), BinaryExpr.Operator.GREATER))) {
            int temp = side2;
            side2 = side3;
            side3 = temp;
        }

        //side1 + side2 <= side3
        if (gym.workout(3, new BinaryExpr(new StringLiteralExpr(String.valueOf(side1 + side2)), new StringLiteralExpr(String.valueOf(side3)), BinaryExpr.Operator.LESS_EQUALS))) {
            type = Type.INVALID;
        } else {
            type = Type.SCALENE;
            //side1 == side2
            if (gym.workout(4, new BinaryExpr(new StringLiteralExpr(String.valueOf(side1)), new StringLiteralExpr(String.valueOf(side2)), BinaryExpr.Operator.EQUALS))) {
                //side2 == side3
                if (gym.workout(5, new BinaryExpr(new StringLiteralExpr(String.valueOf(side2)), new StringLiteralExpr(String.valueOf(side3)), BinaryExpr.Operator.EQUALS))) {
                    type = Type.EQUILATERAL;
                }
            } else {
                //side2 == side3
                if (gym.workout(6, new BinaryExpr(new StringLiteralExpr(String.valueOf(side2)), new StringLiteralExpr(String.valueOf(side3)), BinaryExpr.Operator.EQUALS))) {
                    type = Type.ISOSCELES;
                }
            }
        }
        return type;
    }
}
