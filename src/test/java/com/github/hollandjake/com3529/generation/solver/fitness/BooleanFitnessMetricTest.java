package com.github.hollandjake.com3529.generation.solver.fitness;

import com.github.hollandjake.com3529.generation.ConditionCoverage;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BooleanFitnessMetricTest
{
    private BooleanFitnessMetric fitnessMetric;

    @Before
    public void setUp()
    {
        fitnessMetric = new BooleanFitnessMetric();
    }

    @Test
    public void testEquals()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 1d);
        ConditionCoverage resultTrueTrue = fitnessMetric.equals(0, true, true);
        assertEquals(expectedTrue, resultTrueTrue);
        ConditionCoverage resultFalseFalse = fitnessMetric.equals(0, false, false);
        assertEquals(expectedTrue, resultFalseFalse);

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 1d, 0d);
        ConditionCoverage resultTrueFalse = fitnessMetric.equals(0, true, false);
        assertEquals(expectedFalse, resultTrueFalse);
        ConditionCoverage resultFalseTrue = fitnessMetric.equals(0, false, true);
        assertEquals(expectedFalse, resultFalseTrue);
    }

    @Test
    public void testNotEquals()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 1d);
        ConditionCoverage resultTrueFalse = fitnessMetric.notEquals(0, true, false);
        assertEquals(expectedTrue, resultTrueFalse);
        ConditionCoverage resultFalseTrue = fitnessMetric.notEquals(0, false, true);
        assertEquals(expectedTrue, resultFalseTrue);

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 1d, 0d);
        ConditionCoverage resultTrueTrue = fitnessMetric.notEquals(0, true, true);
        assertEquals(expectedFalse, resultTrueTrue);
        ConditionCoverage resultFalseFalse = fitnessMetric.notEquals(0, false, false);
        assertEquals(expectedFalse, resultFalseFalse);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testLess()
    {
        fitnessMetric.less(0, true, true);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testLessEquals()
    {
        fitnessMetric.lessEquals(0, true, true);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGreater()
    {
        fitnessMetric.greater(0, true, true);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGreaterEquals()
    {
        fitnessMetric.greaterEquals(0, true, true);
    }
}