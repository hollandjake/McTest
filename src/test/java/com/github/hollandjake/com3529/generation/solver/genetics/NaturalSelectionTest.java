package com.github.hollandjake.com3529.generation.solver.genetics;

import java.util.Arrays;

import com.github.hollandjake.com3529.generation.MethodTestSuite;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import lombok.SneakyThrows;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.testng.AssertJUnit.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareOnlyThisForTest
public class NaturalSelectionTest
{
    @BeforeMethod
    @SneakyThrows
    public void setUp()
    {
        mockStatic(NaturalSelection.class);
        PowerMockito.when(NaturalSelection.class, "overPopulation", anyList()).thenCallRealMethod();
        PowerMockito.when(NaturalSelection.class, "TOP_N").thenReturn(2);
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