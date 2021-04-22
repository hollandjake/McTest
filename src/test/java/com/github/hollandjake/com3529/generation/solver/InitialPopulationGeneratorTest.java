package com.github.hollandjake.com3529.generation.solver;

import java.util.List;

import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;

import org.mockito.MockedStatic;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class InitialPopulationGeneratorTest
{
    private MockedStatic<InitialPopulationGenerator> initialPopulationGenerator;

    @BeforeMethod
    public void setUp()
    {
        initialPopulationGenerator = mockStatic(InitialPopulationGenerator.class);
        initialPopulationGenerator.when(InitialPopulationGenerator::INITIAL_NUM_TESTS).thenReturn(2);
        initialPopulationGenerator.when(() -> InitialPopulationGenerator.generate(any(), anyInt())).thenCallRealMethod();
    }

    @AfterMethod
    public void tearDown()
    {
        initialPopulationGenerator.close();
    }

    @Test
    public void testGenerate()
    {
        int populationSize = 5;
        Method method = mock(Method.class);
        java.lang.reflect.Method executableMethod = mock(java.lang.reflect.Method.class);
        when(method.getExecutableMethod()).thenReturn(executableMethod);
        when(executableMethod.getParameterTypes()).thenReturn(new Class<?>[]{int.class, int.class});

        List<MethodTestSuite> output = InitialPopulationGenerator.generate(method, populationSize);

        assertThat(output, hasSize(populationSize));
    }
}