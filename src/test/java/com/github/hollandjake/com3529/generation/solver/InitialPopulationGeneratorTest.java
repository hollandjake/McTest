package com.github.hollandjake.com3529.generation.solver;

import java.util.List;

import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import lombok.SneakyThrows;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareOnlyThisForTest
public class InitialPopulationGeneratorTest
{
    @BeforeMethod
    @SneakyThrows
    public void setUp()
    {
        mockStatic(InitialPopulationGenerator.class);
        PowerMockito.when(InitialPopulationGenerator.class, "INITIAL_NUM_TESTS").thenReturn(2);
        PowerMockito.when(InitialPopulationGenerator.class, "generate", any(), anyInt()).thenCallRealMethod();
    }

    @Test
    public void testGenerate()
    {
        int populationSize = 5;
        Method method = mock(Method.class);
        java.lang.reflect.Method executableMethod = mock(java.lang.reflect.Method.class);
        when(method.getExecutableMethod()).thenReturn(executableMethod);
        when(executableMethod.getParameterTypes()).thenReturn(new Class<?>[] { int.class, int.class });

        List<MethodTestSuite> output = InitialPopulationGenerator.generate(method, populationSize);

        assertThat(output, hasSize(populationSize));
    }
}