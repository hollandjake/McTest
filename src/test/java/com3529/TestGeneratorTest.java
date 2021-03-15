package com3529;

import org.junit.Test;

public class TestGeneratorTest
{
    @Test
    public void shouldGenerateTest()
    {
        TestGenerator.of("Triangle.java");

        // create all branch nested conditions with recursive joins

        // for each branch calculate the boundary conditions for each boolean

        // For each binary expression calculate the boundary conditions for each boolean

        // for each branch calculate truth table of the boolean logic

        // e.g
        // if (a<b && b>=c && c<a)

        //                   T   |  F
        // A = a<b          a<b  | a>=b
        // B = b>=c         b>=c | b<c
        // C = c<a          c<a  | c>=a

        // | A | B | C | OUTPUT |
        // | - | - | - | ------ |
        // | F | F | F |   F    |
        // | F | F | T |   F    |
        // | F | T | F |   F    |
        // | F | T | T |   F    | *
        // | T | F | F |   F    |
        // | T | F | T |   F    |
        // | T | T | F |   F    |
        // | T | T | T |   T    | *

        // | F | T | T | a>=b && b>=c && c<a        a>=b>=c    c<a>=b
        //                                          3>=2>=1    1<3>=2
        // | T | T | T | a<b && b>=c && c<a         a<b>=c     c<a<b
        //                                          2<3>1      1<2<3

        // | T | F | T | a<b && b<c && c<a          a<b<c      c<a<b
        //                                          1<2<3      3<1<2    IMPOSSIBLE
        // | T | T | T | a<b && b>=c && c<a         a<b>=c     c<a<b
        //                                          2<3>=1     1<2<3

        // | T | T | F | a<b && b>=c && c>=a        a<b>=c     c>=a<b
        //                                          1<3>=2     2>=1<3
        // | T | T | T | a<b && b>=c && c<a         a<b>=c     c<a<b
        //                                          2<3>=1     1<2<3
    }
}