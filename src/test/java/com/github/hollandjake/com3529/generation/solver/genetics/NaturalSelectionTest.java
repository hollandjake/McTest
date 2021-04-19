package com.github.hollandjake.com3529.generation.solver.genetics;

import java.util.Arrays;

import com.github.hollandjake.com3529.generation.MethodTestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class NaturalSelectionTest
{
    MockedStatic<NaturalSelection> naturalSelection;
    @Before
    public void setUp()
    {
        naturalSelection = mockStatic(NaturalSelection.class);
        naturalSelection.when(() -> NaturalSelection.overPopulation(anyList())).thenCallRealMethod();
        naturalSelection.when(NaturalSelection::TOP_N).thenReturn(2);
    }

    @After
    public void teardown()
    {
        naturalSelection.close();
    }

    @Test
    public void testOverPopulation()
    {
        MethodTestSuite testSuite1 = mock(MethodTestSuite.class);
        MethodTestSuite testSuite2 = mock(MethodTestSuite.class);
        MethodTestSuite testSuite3 = mock(MethodTestSuite.class);

        when(testSuite1.getFitness()).thenReturn(1d);
        when(testSuite2.getFitness()).thenReturn(2d);
        when(testSuite3.getFitness()).thenReturn(3d);

        assertEquals(
                Arrays.asList(testSuite1,testSuite2),
                NaturalSelection.overPopulation(Arrays.asList(testSuite3, testSuite2, testSuite1))
        );
    }
}