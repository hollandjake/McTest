package com.github.hollandjake.com3529.test;

import com.github.hollandjake.com3529.ClassTestGenerator;

import org.junit.Test;

public class ClassTestGeneratorTest
{
    @Test
    public void shouldGenerateTestsForTriangle()
    {
        ClassTestGenerator.forClass("Triangle.java");
    }

    @Test
    public void shouldGenerateTestsForBMICalculator()
    {
        ClassTestGenerator.forClass("BMICalculator.java");
    }

    @Test
    public void shouldGenerateTestsForStringTest()
    {
        ClassTestGenerator.forClass("StringTest.java");
    }

    @Test
    public void shouldGenerateTestsForShortTest()
    {
        ClassTestGenerator.forClass("ShortTest.java");
    }

    @Test
    public void shouldGenerateTestsForLoopTest()
    {
        ClassTestGenerator.forClass("LoopTest.java");
    }

    @Test
    public void shouldGenerateTestsForLongTest()
    {
        ClassTestGenerator.forClass("LongTest.java");
    }
}