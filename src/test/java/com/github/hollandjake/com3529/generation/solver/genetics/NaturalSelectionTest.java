package com.github.hollandjake.com3529.generation.solver.genetics;

import java.util.Arrays;

import com.github.hollandjake.com3529.generation.MethodTestSuite;

import org.junit.Test;

import lombok.SneakyThrows;

import static com.github.hollandjake.com3529.testutils.TestUtils.setFinalStatic;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NaturalSelectionTest
{
    @Test
    @SneakyThrows
    public void testOverPopulation()
    {
        setFinalStatic(NaturalSelection.class,"TOP_N", 2);
        MethodTestSuite testSuite1 = mock(MethodTestSuite.class);
        MethodTestSuite testSuite2 = mock(MethodTestSuite.class);
        MethodTestSuite testSuite3 = mock(MethodTestSuite.class);

        when(testSuite1.getFitness()).thenReturn(1d);
        when(testSuite2.getFitness()).thenReturn(2d);
        when(testSuite3.getFitness()).thenReturn(3d);

        assertEquals(
                Arrays.asList(testSuite3,testSuite2),
                NaturalSelection.overPopulation(Arrays.asList(testSuite1, testSuite2, testSuite3))
        );
    }
}