package com.github.hollandjake.com3529.generation.solver.fitness;

import com.github.hollandjake.com3529.generation.ConditionCoverage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class StringFitnessMetricTest
{
    private StringFitnessMetric fitnessMetric;

    @BeforeMethod
    public void setUp()
    {
        fitnessMetric = new StringFitnessMetric();
    }

    @Test
    public void testEquals()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 1d);
        assertEquals(expectedTrue, fitnessMetric.equals(0, "hello", "hello"));

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 4d, 0d);
        assertEquals(expectedFalse, fitnessMetric.equals(0, "hello", "world"));

        ConditionCoverage expectedFalseDifferentLength = new ConditionCoverage(0, false, 393210d, 0d);
        assertEquals(expectedFalseDifferentLength, fitnessMetric.equals(0, "hello", "hello there"));
    }

    @Test
    public void testNotEquals()
    {
        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 1d, 0d);
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, "hello", "hello"));

        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 4d);
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, "hello", "world"));

        ConditionCoverage expectedTrueDifferentLength = new ConditionCoverage(0, true, 0d, 393210d);
        assertEquals(expectedTrueDifferentLength, fitnessMetric.notEquals(0, "hello", "hello there"));
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testLess()
    {
        fitnessMetric.less(0, "hello", "hello");
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testLessEquals()
    {
        fitnessMetric.lessEquals(0, "hello", "hello");
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testGreater()
    {
        fitnessMetric.greater(0, "hello", "hello");
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testGreaterEquals()
    {
        fitnessMetric.greaterEquals(0, "hello", "hello");
    }
}