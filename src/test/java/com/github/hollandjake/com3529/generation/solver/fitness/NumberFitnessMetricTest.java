package com.github.hollandjake.com3529.generation.solver.fitness;

import com.github.hollandjake.com3529.generation.ConditionCoverage;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumberFitnessMetricTest
{
    private NumberFitnessMetric fitnessMetric;

    @Before
    public void setUp()
    {
        fitnessMetric = new NumberFitnessMetric();
    }

    @Test
    public void testEquals()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 1d);
        assertEquals(expectedTrue, fitnessMetric.equals(0, 1, 1));
        assertEquals(expectedTrue, fitnessMetric.equals(0, Integer.valueOf(1), Integer.valueOf(1)));
        assertEquals(expectedTrue, fitnessMetric.equals(0, 1d, 1d));
        assertEquals(expectedTrue, fitnessMetric.equals(0, 1D, 1D));
        assertEquals(expectedTrue, fitnessMetric.equals(0, 1f, 1f));
        assertEquals(expectedTrue, fitnessMetric.equals(0, 1F, 1F));
        assertEquals(expectedTrue, fitnessMetric.equals(0, (long) 1, (long) 1));
        assertEquals(expectedTrue, fitnessMetric.equals(0, 1L, 1L));

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 1d, 0d);
        assertEquals(expectedFalse, fitnessMetric.equals(0, 1, 0));
        assertEquals(expectedFalse, fitnessMetric.equals(0, Integer.valueOf(1), Integer.valueOf(0)));
        assertEquals(expectedFalse, fitnessMetric.equals(0, 1d, 0d));
        assertEquals(expectedFalse, fitnessMetric.equals(0, 1D, 0D));
        assertEquals(expectedFalse, fitnessMetric.equals(0, 1f, 0f));
        assertEquals(expectedFalse, fitnessMetric.equals(0, 1F, 0F));
        assertEquals(expectedFalse, fitnessMetric.equals(0, (long) 1, (long) 0));
        assertEquals(expectedFalse, fitnessMetric.equals(0, 1L, 0L));
    }

    @Test
    public void testNotEquals()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 1d);
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, 1, 0));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, Integer.valueOf(1), Integer.valueOf(0)));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, 1d, 0d));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, 1D, 0D));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, 1f, 0f));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, 1F, 0F));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, (long) 1, (long) 0));
        assertEquals(expectedTrue, fitnessMetric.notEquals(0, 1L, 0L));

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 1d, 0d);
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, 1, 1));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, Integer.valueOf(1), Integer.valueOf(1)));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, 1d, 1d));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, 1D, 1D));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, 1f, 1f));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, 1F, 1F));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, (long) 1, (long) 1));
        assertEquals(expectedFalse, fitnessMetric.notEquals(0, 1L, 1L));
    }

    @Test
    public void testLess()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 3d);
        assertEquals(expectedTrue, fitnessMetric.less(0, 1, 3));
        assertEquals(expectedTrue, fitnessMetric.less(0, Integer.valueOf(1), Integer.valueOf(3)));
        assertEquals(expectedTrue, fitnessMetric.less(0, 1d, 3d));
        assertEquals(expectedTrue, fitnessMetric.less(0, 1D, 3D));
        assertEquals(expectedTrue, fitnessMetric.less(0, 1f, 3f));
        assertEquals(expectedTrue, fitnessMetric.less(0, 1F, 3F));
        assertEquals(expectedTrue, fitnessMetric.less(0, (long) 1, (long) 3));
        assertEquals(expectedTrue, fitnessMetric.less(0, 1L, 3L));

        ConditionCoverage expectedFalseEquals = new ConditionCoverage(0, false, 1d, 0d);
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, 1, 1));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, Integer.valueOf(1), Integer.valueOf(1)));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, 1d, 1d));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, 1D, 1D));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, 1f, 1f));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, 1F, 1F));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, (long) 1, (long) 1));
        assertEquals(expectedFalseEquals, fitnessMetric.less(0, 1L, 1L));

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 2d, 0d);
        assertEquals(expectedFalse, fitnessMetric.less(0, 1, 0));
        assertEquals(expectedFalse, fitnessMetric.less(0, Integer.valueOf(1), Integer.valueOf(0)));
        assertEquals(expectedFalse, fitnessMetric.less(0, 1d, 0d));
        assertEquals(expectedFalse, fitnessMetric.less(0, 1D, 0D));
        assertEquals(expectedFalse, fitnessMetric.less(0, 1f, 0f));
        assertEquals(expectedFalse, fitnessMetric.less(0, 1F, 0F));
        assertEquals(expectedFalse, fitnessMetric.less(0, (long) 1, (long) 0));
        assertEquals(expectedFalse, fitnessMetric.less(0, 1L, 0L));
    }

    @Test
    public void testLessEquals()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 3d);
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, 1, 3));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, Integer.valueOf(1), Integer.valueOf(3)));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, 1d, 3d));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, 1D, 3D));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, 1f, 3f));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, 1F, 3F));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, (long) 1, (long) 3));
        assertEquals(expectedTrue, fitnessMetric.lessEquals(0, 1L, 3L));

        ConditionCoverage expectedTrueEquals = new ConditionCoverage(0, true, 0d, 1d);
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, 1, 1));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, Integer.valueOf(1), Integer.valueOf(1)));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, 1d, 1d));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, 1D, 1D));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, 1f, 1f));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, 1F, 1F));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, (long) 1, (long) 1));
        assertEquals(expectedTrueEquals, fitnessMetric.lessEquals(0, 1L, 1L));

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 2d, 0d);
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, 1, 0));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, Integer.valueOf(1), Integer.valueOf(0)));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, 1d, 0d));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, 1D, 0D));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, 1f, 0f));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, 1F, 0F));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, (long) 1, (long) 0));
        assertEquals(expectedFalse, fitnessMetric.lessEquals(0, 1L, 0L));
    }

    @Test
    public void testGreater()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 3d);
        assertEquals(expectedTrue, fitnessMetric.greater(0, 3, 1));
        assertEquals(expectedTrue, fitnessMetric.greater(0, Integer.valueOf(3), Integer.valueOf(1)));
        assertEquals(expectedTrue, fitnessMetric.greater(0, 3d, 1d));
        assertEquals(expectedTrue, fitnessMetric.greater(0, 3D, 1D));
        assertEquals(expectedTrue, fitnessMetric.greater(0, 3f, 1f));
        assertEquals(expectedTrue, fitnessMetric.greater(0, 3F, 1F));
        assertEquals(expectedTrue, fitnessMetric.greater(0, (long) 3, (long) 1));
        assertEquals(expectedTrue, fitnessMetric.greater(0, 3L, 1L));

        ConditionCoverage expectedFalseEquals = new ConditionCoverage(0, false, 1d, 0d);
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, 1, 1));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, Integer.valueOf(1), Integer.valueOf(1)));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, 1d, 1d));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, 1D, 1D));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, 1f, 1f));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, 1F, 1F));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, (long) 1, (long) 1));
        assertEquals(expectedFalseEquals, fitnessMetric.greater(0, 1L, 1L));

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 2d, 0d);
        assertEquals(expectedFalse, fitnessMetric.greater(0, 0, 1));
        assertEquals(expectedFalse, fitnessMetric.greater(0, Integer.valueOf(0), Integer.valueOf(1)));
        assertEquals(expectedFalse, fitnessMetric.greater(0, 0d, 1d));
        assertEquals(expectedFalse, fitnessMetric.greater(0, 0D, 1D));
        assertEquals(expectedFalse, fitnessMetric.greater(0, 0f, 1f));
        assertEquals(expectedFalse, fitnessMetric.greater(0, 0F, 1F));
        assertEquals(expectedFalse, fitnessMetric.greater(0, (long) 0, (long) 1));
        assertEquals(expectedFalse, fitnessMetric.greater(0, 0L, 1L));
    }

    @Test
    public void testGreaterEquals()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 3d);
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, 3, 1));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, Integer.valueOf(3), Integer.valueOf(1)));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, 3d, 1d));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, 3D, 1D));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, 3f, 1f));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, 3F, 1F));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, (long) 3, (long) 1));
        assertEquals(expectedTrue, fitnessMetric.greaterEquals(0, 3L, 1L));

        ConditionCoverage expectedTrueEquals = new ConditionCoverage(0, true, 0d, 1d);
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, 1, 1));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, Integer.valueOf(1), Integer.valueOf(1)));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, 1d, 1d));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, 1D, 1D));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, 1f, 1f));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, 1F, 1F));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, (long) 1, (long) 1));
        assertEquals(expectedTrueEquals, fitnessMetric.greaterEquals(0, 1L, 1L));

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 2d, 0d);
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, 0, 1));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, Integer.valueOf(0), Integer.valueOf(1)));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, 0d, 1d));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, 0D, 1D));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, 0f, 1f));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, 0F, 1F));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, (long) 0, (long) 1));
        assertEquals(expectedFalse, fitnessMetric.greaterEquals(0, 0L, 1L));
    }
}