package com.github.hollandjake.com3529.generation.solver.fitness;

import com.github.hollandjake.com3529.generation.ConditionCoverage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class NumberFitnessMetricTest
{
    private NumberFitnessMetric fitnessMetric;

    @BeforeMethod
    public void setUp()
    {
        fitnessMetric = new NumberFitnessMetric();
    }

    @Test
    public void testEquals()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 1d);
        assertEquals(expectedTrue, fitnessMetric.equals(0, 1, 1));
        assertEquals(expectedTrue, fitnessMetric.equals(0, 1, 1));
        assertEquals(expectedTrue, fitnessMetric.equals(0, 1d, 1d));
        assertEquals(expectedTrue, fitnessMetric.equals(0, 1D, 1D));
        assertEquals(expectedTrue, fitnessMetric.equals(0, 1f, 1f));
        assertEquals(expectedTrue, fitnessMetric.equals(0, 1F, 1F));
        assertEquals(expectedTrue, fitnessMetric.equals(0, (long) 1, (long) 1));
        assertEquals(expectedTrue, fitnessMetric.equals(0, 1L, 1L));
        assertEquals(expectedTrue, fitnessMetric.equals(0, (byte) 1, (byte) 1));
        assertEquals(expectedTrue, fitnessMetric.equals(0, (byte) 1, (byte) 1));
        assertEquals(expectedTrue, fitnessMetric.equals(0, (short) 1, (short) 1));
        assertEquals(expectedTrue, fitnessMetric.equals(0, (short) 1, (short) 1));

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 1d, 0d);
        assertEquals(expectedFalse, fitnessMetric.equals(0, 1, 0));
        assertEquals(expectedFalse, fitnessMetric.equals(0, 1, 0));
        assertEquals(expectedFalse, fitnessMetric.equals(0, 1d, 0d));
        assertEquals(expectedFalse, fitnessMetric.equals(0, 1D, 0D));
        assertEquals(expectedFalse, fitnessMetric.equals(0, 1f, 0f));
        assertEquals(expectedFalse, fitnessMetric.equals(0, 1F, 0F));
        assertEquals(expectedFalse, fitnessMetric.equals(0, (long) 1, (long) 0));
        assertEquals(expectedFalse, fitnessMetric.equals(0, 1L, 0L));
        assertEquals(expectedFalse, fitnessMetric.equals(0, (byte) 1, (byte) 0));
        assertEquals(expectedFalse, fitnessMetric.equals(0, (byte) 1, (byte) 0));
        assertEquals(expectedFalse, fitnessMetric.equals(0, (short) 1, (short) 0));
        assertEquals(expectedFalse, fitnessMetric.equals(0, (short) 1, (short) 0));
    }

    @Test
    public void testNotEquals()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 1d);
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, 1, 0));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, 1, 0));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, 1d, 0d));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, 1D, 0D));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, 1f, 0f));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, 1F, 0F));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, (long) 1, (long) 0));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, 1L, 0L));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, (byte) 1, (byte) 0));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, (byte) 1, (byte) 0));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, (short) 1, (short) 0));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, (short) 1, (short) 0));

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 1d, 0d);
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, 1, 1));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, 1, 1));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, 1d, 1d));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, 1D, 1D));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, 1f, 1f));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, 1F, 1F));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, (long) 1, (long) 1));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, 1L, 1L));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, (byte) 1, (byte) 1));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, (byte) 1, (byte) 1));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, (short) 1, (short) 1));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, (short) 1, (short) 1));
    }

    @Test
    public void testLess()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 3d);
        assertEquals(expectedTrue, fitnessMetric.less(0, 1, 3));
        assertEquals(expectedTrue, fitnessMetric.less(0, 1, 3));
        assertEquals(expectedTrue, fitnessMetric.less(0, 1d, 3d));
        assertEquals(expectedTrue, fitnessMetric.less(0, 1D, 3D));
        assertEquals(expectedTrue, fitnessMetric.less(0, 1f, 3f));
        assertEquals(expectedTrue, fitnessMetric.less(0, 1F, 3F));
        assertEquals(expectedTrue, fitnessMetric.less(0, (long) 1, (long) 3));
        assertEquals(expectedTrue, fitnessMetric.less(0, 1L, 3L));
        assertEquals(expectedTrue, fitnessMetric.less(0, (byte) 1, (byte) 3));
        assertEquals(expectedTrue, fitnessMetric.less(0, (byte) 1, (byte) 3));
        assertEquals(expectedTrue, fitnessMetric.less(0, (short) 1, (short) 3));
        assertEquals(expectedTrue, fitnessMetric.less(0, (short) 1, (short) 3));

        ConditionCoverage expectedFalseEquals = new ConditionCoverage(0, false, 1d, 0d);
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, 1, 1));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, 1, 1));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, 1d, 1d));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, 1D, 1D));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, 1f, 1f));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, 1F, 1F));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, (long) 1, (long) 1));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, 1L, 1L));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, (byte) 1, (byte) 1));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, (byte) 1, (byte) 1));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, (short) 1, (short) 1));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, (short) 1, (short) 1));

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 2d, 0d);
        assertEquals(expectedFalse, fitnessMetric.less(0, 1, 0));
        assertEquals(expectedFalse, fitnessMetric.less(0, 1, 0));
        assertEquals(expectedFalse, fitnessMetric.less(0, 1d, 0d));
        assertEquals(expectedFalse, fitnessMetric.less(0, 1D, 0D));
        assertEquals(expectedFalse, fitnessMetric.less(0, 1f, 0f));
        assertEquals(expectedFalse, fitnessMetric.less(0, 1F, 0F));
        assertEquals(expectedFalse, fitnessMetric.less(0, (long) 1, (long) 0));
        assertEquals(expectedFalse, fitnessMetric.less(0, 1L, 0L));
        assertEquals(expectedFalse, fitnessMetric.less(0, (byte) 1, (byte) 0));
        assertEquals(expectedFalse, fitnessMetric.less(0, (byte) 1, (byte) 0));
        assertEquals(expectedFalse, fitnessMetric.less(0, (short) 1, (short) 0));
        assertEquals(expectedFalse, fitnessMetric.less(0, (short) 1, (short) 0));
    }

    @Test
    public void testLessEquals()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 3d);
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, 1, 3));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, 1, 3));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, 1d, 3d));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, 1D, 3D));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, 1f, 3f));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, 1F, 3F));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, (long) 1, (long) 3));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, 1L, 3L));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, (byte) 1, (byte) 3));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, (byte) 1, (byte) 3));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, (short) 1, (short) 3));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, (short) 1, (short) 3));

        ConditionCoverage expectedTrueEquals = new ConditionCoverage(0, true, 0d, 1d);
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, 1, 1));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, 1, 1));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, 1d, 1d));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, 1D, 1D));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, 1f, 1f));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, 1F, 1F));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, (long) 1, (long) 1));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, 1L, 1L));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, (byte) 1, (byte) 1));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, (byte) 1, (byte) 1));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, (short) 1, (short) 1));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, (short) 1, (short) 1));

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 2d, 0d);
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, 1, 0));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, 1, 0));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, 1d, 0d));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, 1D, 0D));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, 1f, 0f));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, 1F, 0F));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, (long) 1, (long) 0));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, 1L, 0L));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, (byte) 1, (byte) 0));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, (byte) 1, (byte) 0));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, (short) 1, (short) 0));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, (short) 1, (short) 0));
    }

    @Test
    public void testGreater()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 3d);
        assertEquals(expectedTrue, fitnessMetric.greater(0, 3, 1));
        assertEquals(expectedTrue, fitnessMetric.greater(0, 3, 1));
        assertEquals(expectedTrue, fitnessMetric.greater(0, 3d, 1d));
        assertEquals(expectedTrue, fitnessMetric.greater(0, 3D, 1D));
        assertEquals(expectedTrue, fitnessMetric.greater(0, 3f, 1f));
        assertEquals(expectedTrue, fitnessMetric.greater(0, 3F, 1F));
        assertEquals(expectedTrue, fitnessMetric.greater(0, (long) 3, (long) 1));
        assertEquals(expectedTrue, fitnessMetric.greater(0, 3L, 1L));
        assertEquals(expectedTrue, fitnessMetric.greater(0, (byte) 3, (byte) 1));
        assertEquals(expectedTrue, fitnessMetric.greater(0, (byte) 3, (byte) 1));
        assertEquals(expectedTrue, fitnessMetric.greater(0, (short) 3, (short) 1));
        assertEquals(expectedTrue, fitnessMetric.greater(0, (short) 3, (short) 1));

        ConditionCoverage expectedFalseEquals = new ConditionCoverage(0, false, 1d, 0d);
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, 1, 1));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, 1, 1));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, 1d, 1d));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, 1D, 1D));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, 1f, 1f));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, 1F, 1F));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, (long) 1, (long) 1));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, 1L, 1L));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, (byte) 1, (byte) 1));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, (byte) 1, (byte) 1));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, (short) 1, (short) 1));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, (short) 1, (short) 1));

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 2d, 0d);
        assertEquals(expectedFalse, fitnessMetric.greater(0, 0, 1));
        assertEquals(expectedFalse, fitnessMetric.greater(0, 0, 1));
        assertEquals(expectedFalse, fitnessMetric.greater(0, 0d, 1d));
        assertEquals(expectedFalse, fitnessMetric.greater(0, 0D, 1D));
        assertEquals(expectedFalse, fitnessMetric.greater(0, 0f, 1f));
        assertEquals(expectedFalse, fitnessMetric.greater(0, 0F, 1F));
        assertEquals(expectedFalse, fitnessMetric.greater(0, (long) 0, (long) 1));
        assertEquals(expectedFalse, fitnessMetric.greater(0, 0L, 1L));
        assertEquals(expectedFalse, fitnessMetric.greater(0, (byte) 0, (byte) 1));
        assertEquals(expectedFalse, fitnessMetric.greater(0, (byte) 0, (byte) 1));
        assertEquals(expectedFalse, fitnessMetric.greater(0, (short) 0, (short) 1));
        assertEquals(expectedFalse, fitnessMetric.greater(0, (short) 0, (short) 1));
    }

    @Test
    public void testGreaterEquals()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 3d);
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, 3, 1));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, 3, 1));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, 3d, 1d));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, 3D, 1D));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, 3f, 1f));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, 3F, 1F));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, (long) 3, (long) 1));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, 3L, 1L));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, (byte) 3, (byte) 1));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, (byte) 3, (byte) 1));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, (short) 3, (short) 1));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, (short) 3, (short) 1));

        ConditionCoverage expectedTrueEquals = new ConditionCoverage(0, true, 0d, 1d);
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, 1, 1));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, 1, 1));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, 1d, 1d));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, 1D, 1D));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, 1f, 1f));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, 1F, 1F));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, (long) 1, (long) 1));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, 1L, 1L));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, (byte) 1, (byte) 1));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, (byte) 1, (byte) 1));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, (short) 1, (short) 1));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, (short) 1, (short) 1));

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 2d, 0d);
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, 0, 1));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, 0, 1));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, 0d, 1d));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, 0D, 1D));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, 0f, 1f));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, 0F, 1F));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, (long) 0, (long) 1));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, 0L, 1L));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, (byte) 0, (byte) 1));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, (byte) 0, (byte) 1));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, (short) 0, (short) 1));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, (short) 0, (short) 1));
    }
}