package com.github.hollandjake.com3529.generation.solver;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.generation.TestCase;
import com.github.hollandjake.com3529.generation.solver.mutation.InputMutator;
import com.typesafe.config.ConfigFactory;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Breed
{
    private static final Random RANDOM = new Random();
    private static final double CROSSOVER_SELECTION_PROBABILITY = ConfigFactory.load().getDouble("Genetics.CrossoverSelectionProbability");
    private static final double MUTATION_PROBABILITY = ConfigFactory.load().getDouble("Genetics.MutationProbability");

    public static List<MethodTestSuite> repopulate(Method method,
            List<MethodTestSuite> oldPopulation,
            int populationSize)
    {
        return IntStream.range(oldPopulation.size(), populationSize).parallel().mapToObj(p -> {
            MethodTestSuite parentA = oldPopulation.get(RANDOM.nextInt(oldPopulation.size()));
            MethodTestSuite parentB = oldPopulation.get(RANDOM.nextInt(oldPopulation.size()));

            TestCase[] parentATests = parentA.getTests().toArray(new TestCase[0]);
            TestCase[] parentBTests = parentB.getTests().toArray(new TestCase[0]);

            Set<TestCase> testCases = mutate(crossover(parentATests, parentBTests));

            return new MethodTestSuite(method, testCases);
        }).collect(Collectors.toList());
    }

    private static Set<TestCase> crossover(TestCase[] parentA, TestCase[] parentB)
    {
        //Uniform crossover
        int parentALength = parentA.length;
        int parentBLength = parentB.length;
        int maxTests = Math.max(parentALength, parentBLength);

        Set<TestCase> testCases = new HashSet<>();

        for (int i = 0; i < maxTests; i++)
        {
            if (i < parentALength && RANDOM.nextDouble() <= CROSSOVER_SELECTION_PROBABILITY)
            {
                testCases.add(parentA[i]);
            }

            if (i < parentBLength && RANDOM.nextDouble() <= CROSSOVER_SELECTION_PROBABILITY)
            {
                testCases.add(parentB[i]);
            }
        }
        return testCases;
    }

    private static Set<TestCase> mutate(Set<TestCase> tests)
    {
        //Uniform mutation
        Set<TestCase> newTestCases = new HashSet<>();
        for (TestCase testCase : tests)
        {
            boolean hasChanged = false;
            Object[] newInputs = new Object[testCase.getInputs().length];
            for (int z = 0; z < testCase.getInputs().length; z++)
            {
                Object input = testCase.getInputs()[z];
                if (RANDOM.nextDouble() <= MUTATION_PROBABILITY)
                {
                    double rand = RANDOM.nextDouble();
                    Object newInput;
                    double offset = RANDOM.nextGaussian();
                    if (rand < 2 / 3d)
                    {
                        //offset can be negative so it handles both +offset and -offset
                        newInput = InputMutator.add(input, offset);
                    }
                    else
                    {
                        newInput = InputMutator.generate(input.getClass());
                    }
                    newInputs[z] = newInput;
                    hasChanged = true;
                }
                else
                {
                    newInputs[z] = input;
                }
            }
            if (hasChanged)
            {
                newTestCases.add(new TestCase(testCase.getMethod(), newInputs));
            }
            else
            {
                newTestCases.add(testCase);
            }
        }
        return newTestCases;
    }
}
