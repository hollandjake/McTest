package com.github.hollandjake.com3529.generation.solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.generation.TestCase;
import com.typesafe.config.ConfigFactory;

import lombok.experimental.UtilityClass;

@UtilityClass
public class InitialPopulationGenerator
{
    private static final int INITIAL_NUM_TESTS = ConfigFactory.load().getInt("Genetics.Initial.NumTests");

    public static List<MethodTestSuite> generate(Method method, int populationSize)
    {
        List<MethodTestSuite> population = new ArrayList<>();
        Class<?>[] methodParameterTypes = method.getExecutableMethod().getParameterTypes();

        //Skip last one as that is the CoverageReport object
        int numInputs = methodParameterTypes.length - 1;

        for (int p = 0; p < populationSize; p++)
        {
            List<Object[]> suiteInputs = new ArrayList<>();
            for (int t = 0; t < INITIAL_NUM_TESTS; t++)
            {
                Object[] testInputs = new Object[numInputs];
                for (int j = 0; j < numInputs; j++)
                {
                    testInputs[j] = InputGenerator.generate(methodParameterTypes[j]);
                }
                suiteInputs.add(testInputs);
            }
            population.add(createSuite(method, suiteInputs));
        }

        return population;
    }

    private static MethodTestSuite createSuite(Method method, List<Object[]> inputs)
    {
        Set<TestCase> tests = inputs.stream()
                                    .map(inputSequence -> createTestCase(method, inputSequence))
                                    .collect(Collectors.toSet());

        return new MethodTestSuite(method, tests);
    }

    private static TestCase createTestCase(Method method, Object[] inputs)
    {
        return new TestCase(method, inputs);
    }
}
