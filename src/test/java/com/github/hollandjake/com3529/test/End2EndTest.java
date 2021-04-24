package com.github.hollandjake.com3529.test;

import com.github.hollandjake.com3529.ClassTestGenerator;
import com.github.hollandjake.com3529.MethodTestGenerator;

import org.mockito.MockedStatic;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mockStatic;

@Test(groups = "E2E", singleThreaded = true)
public class End2EndTest
{
    private MockedStatic<MethodTestGenerator> methodTestGenerator;

    @BeforeMethod
    public void setUp()
    {
        methodTestGenerator = mockStatic(MethodTestGenerator.class);
        methodTestGenerator.when(MethodTestGenerator::POPULATION_SIZE).thenReturn(100);
        methodTestGenerator.when(MethodTestGenerator::TARGET_FITNESS).thenReturn(0d);
        methodTestGenerator.when(MethodTestGenerator::MAX_ITERATIONS).thenReturn(1000L);
    }

    @AfterMethod
    public void tearDown()
    {
        methodTestGenerator.close();
    }

    @Test
    public void shouldGenerateTestsForTriangle()
    {
        ClassTestGenerator.forClass("Triangle.java", "../generatedTests");
    }

    @Test
    public void shouldGenerateTestsForBMICalculator()
    {
        ClassTestGenerator.forClass("BMICalculator.java", "../generatedTests");
    }

    @Test
    public void shouldGenerateTestsForStringTest()
    {
        ClassTestGenerator.forClass("StringTest.java", "../generatedTests");
    }

    @Test
    public void shouldGenerateTestsForShortTest()
    {
        ClassTestGenerator.forClass("ShortTest.java", "../generatedTests");
    }

    @Test
    public void shouldGenerateTestsForLoopTest()
    {
        ClassTestGenerator.forClass("LoopTest.java", "../generatedTests");
    }

    @Test
    public void shouldGenerateTestsForLongTest()
    {
        ClassTestGenerator.forClass("LongTest.java", "../generatedTests");
    }
}