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
}