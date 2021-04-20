package com.github.hollandjake.com3529.test;

import com.github.hollandjake.com3529.ClassTestGenerator;
import com.github.hollandjake.com3529.MethodTestGenerator;

import org.mockito.MockedStatic;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mockStatic;

public class ClassTestGeneratorTest
{
    private MockedStatic<MethodTestGenerator> methodTestGenerator;

    @BeforeMethod
    public void setUp()
    {
        methodTestGenerator = mockStatic(MethodTestGenerator.class);
        methodTestGenerator.when(MethodTestGenerator::POPULATION_SIZE).thenReturn(100);
        methodTestGenerator.when(MethodTestGenerator::TARGET_FITNESS).thenReturn(0d);
        methodTestGenerator.when(MethodTestGenerator::MAX_ITERATIONS).thenReturn(100L);
    }

    @AfterMethod
    public void tearDown()
    {
        methodTestGenerator.close();
    }

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