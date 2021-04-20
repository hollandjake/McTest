package com.github.hollandjake.com3529.generation.solver.fitness;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertThrows;

public class FitnessMetricTest
{
    @Test
    public void testGetMetricFor()
    {
        assertThat(FitnessMetric.getMetricFor(true, true), instanceOf(BooleanFitnessMetric.class));
        assertThat(FitnessMetric.getMetricFor('a', 'a'), instanceOf(CharacterFitnessMetric.class));
        assertThat(FitnessMetric.getMetricFor("hello", "hello"), instanceOf(StringFitnessMetric.class));
        assertThat(FitnessMetric.getMetricFor(1, 1), instanceOf(NumberFitnessMetric.class));
        assertThat(FitnessMetric.getMetricFor(1, 1L), instanceOf(NumberFitnessMetric.class));
    }

    @Test
    public void testGetMetricForMismatchedTypes()
    {
        assertThrows(UnsupportedOperationException.class, () -> FitnessMetric.getMetricFor(true, 'a'));
        assertThrows(UnsupportedOperationException.class, () -> FitnessMetric.getMetricFor(1, 'a'));
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testGetMetricForUnsupportedTypes()
    {
        FitnessMetric.getMetricFor(new Object(), new Object());
    }
}