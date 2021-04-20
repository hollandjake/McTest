package com.github.hollandjake.com3529.generation.solver.fitness;

import com.github.hollandjake.com3529.generation.ConditionCoverage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class CharacterFitnessMetricTest
{
    private CharacterFitnessMetric fitnessMetric;

    @BeforeMethod
    public void setUp()
    {
        fitnessMetric = new CharacterFitnessMetric();
    }

    @Test
    public void testEquals()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 1d);
        ConditionCoverage resultTrue = fitnessMetric.equals(0, 'a', 'a');
        assertEquals(expectedTrue, resultTrue);

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 2d, 0d);
        ConditionCoverage resultFalse = fitnessMetric.equals(0, 'a', 'c');
        assertEquals(expectedFalse, resultFalse);
    }

    @Test
    public void testNotEquals()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 2d);
        ConditionCoverage resultTrue = fitnessMetric.notEquals(0, 'a', 'c');
        assertEquals(expectedTrue, resultTrue);

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 1d, 0d);
        ConditionCoverage resultFalse = fitnessMetric.notEquals(0, 'd', 'd');
        assertEquals(expectedFalse, resultFalse);
    }

    @Test
    public void testLess()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 3d);
        ConditionCoverage resultTrue = fitnessMetric.less(0, 'a', 'c');
        assertEquals(expectedTrue, resultTrue);

        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 1d, 0d);
        ConditionCoverage resultFalse = fitnessMetric.less(0, 'd', 'd');
        assertEquals(expectedFalse, resultFalse);

        ConditionCoverage expectedBigFalse = new ConditionCoverage(0, false, 2d, 0d);
        ConditionCoverage resultBigFalse = fitnessMetric.less(0, 'e', 'd');
        assertEquals(expectedBigFalse, resultBigFalse);
    }

    @Test
    public void testLessEquals()
    {
        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 3d);
        ConditionCoverage resultTrue = fitnessMetric.lessEquals(0, 'a', 'c');
        assertEquals(expectedTrue, resultTrue);

        ConditionCoverage expectedFalse = new ConditionCoverage(0, true, 0d, 1d);
        ConditionCoverage resultFalse = fitnessMetric.lessEquals(0, 'd', 'd');
        assertEquals(expectedFalse, resultFalse);

        ConditionCoverage expectedBigFalse = new ConditionCoverage(0, false, 2d, 0d);
        ConditionCoverage resultBigFalse = fitnessMetric.lessEquals(0, 'e', 'd');
        assertEquals(expectedBigFalse, resultBigFalse);
    }

    @Test
    public void testGreater()
    {
        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 3d, 0d);
        ConditionCoverage resultFalse = fitnessMetric.greater(0, 'a', 'c');
        assertEquals(expectedFalse, resultFalse);

        ConditionCoverage expectedTrue = new ConditionCoverage(0, false, 1d, 0d);
        ConditionCoverage resultTrue = fitnessMetric.greater(0, 'd', 'd');
        assertEquals(expectedTrue, resultTrue);

        ConditionCoverage expectedBigTrue = new ConditionCoverage(0, true, 0d, 2d);
        ConditionCoverage resultBigTrue = fitnessMetric.greater(0, 'e', 'd');
        assertEquals(expectedBigTrue, resultBigTrue);
    }

    @Test
    public void testGreaterEquals()
    {
        ConditionCoverage expectedFalse = new ConditionCoverage(0, false, 3d, 0d);
        ConditionCoverage resultFalse = fitnessMetric.greaterEquals(0, 'a', 'c');
        assertEquals(expectedFalse, resultFalse);

        ConditionCoverage expectedTrue = new ConditionCoverage(0, true, 0d, 1d);
        ConditionCoverage resultTrue = fitnessMetric.greaterEquals(0, 'd', 'd');
        assertEquals(expectedTrue, resultTrue);

        ConditionCoverage expectedBigTrue = new ConditionCoverage(0, true, 0d, 2d);
        ConditionCoverage resultBigTrue = fitnessMetric.greaterEquals(0, 'e', 'd');
        assertEquals(expectedBigTrue, resultBigTrue);
    }
}