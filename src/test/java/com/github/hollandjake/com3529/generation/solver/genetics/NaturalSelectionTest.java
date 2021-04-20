package com.github.hollandjake.com3529.generation.solver.genetics;

import java.util.Arrays;

import com.github.hollandjake.com3529.generation.MethodTestSuite;

import org.mockito.MockedStatic;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

public class NaturalSelectionTest
{
    MockedStatic<NaturalSelection> naturalSelection;

    @BeforeMethod
    public void setUp()
    {
        naturalSelection = mockStatic(NaturalSelection.class);
        naturalSelection.when(() -> NaturalSelection.overPopulation(anyList())).thenCallRealMethod();
        naturalSelection.when(NaturalSelection::TOP_N).thenReturn(2);
    }

    @AfterMethod
    public void tearDown()
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