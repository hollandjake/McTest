package com.github.hollandjake.com3529.generation.solver;

import java.security.SecureRandom;
import java.util.ArrayList;
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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Breed
{
    @Accessors(fluent = true)
    @Getter(value = AccessLevel.PACKAGE,
            lazy = true)
    private static final Random RANDOM = new SecureRandom();

    @Accessors(fluent = true)
    @Getter(value = AccessLevel.PACKAGE,
            lazy = true)
    private static final double CROSSOVER_SELECTION_PROBABILITY = ConfigFactory.load().getDouble(
            "Genetics.CrossoverSelectionProbability");

    @Accessors(fluent = true)
    @Getter(value = AccessLevel.PACKAGE,
            lazy = true)
    private static final double MUTATION_PROBABILITY = ConfigFactory.load().getDouble("Genetics.MutationProbability");

    public static List<MethodTestSuite> repopulate(Method method,
            List<MethodTestSuite> oldPopulation,
            int populationSize)
    {
        List<MethodTestSuite> tests = new ArrayList<>(oldPopulation);

        tests.addAll(IntStream.range(oldPopulation.size(), populationSize).parallel().mapToObj(p -> {
            MethodTestSuite parentA = oldPopulation.get(RANDOM().nextInt(oldPopulation.size()));
            MethodTestSuite parentB = oldPopulation.get(RANDOM().nextInt(oldPopulation.size()));

            Set<TestCase> testCases = mutate(crossover(parentA.getTests(), parentB.getTests()));

            return new MethodTestSuite(method, testCases);
        }).collect(Collectors.toList()));

        return tests.stream().limit(populationSize).collect(Collectors.toList());
    }

    static Set<TestCase> crossover(Set<TestCase> parentA, Set<TestCase> parentB)
    {
        //Uniform crossover
        List<TestCase> parentATests = new ArrayList<>(parentA);
        List<TestCase> parentBTests = new ArrayList<>(parentB);

        int parentALength = parentA.size();
        int parentBLength = parentB.size();
        int maxTests = Math.max(parentALength, parentBLength);

        Set<TestCase> testCases = new HashSet<>();

        for (int i = 0; i < maxTests; i++)
        {
            if (i < parentALength && RANDOM().nextDouble() <= CROSSOVER_SELECTION_PROBABILITY())
            {
                testCases.add(parentATests.get(i));
            }

            if (i < parentBLength && RANDOM().nextDouble() <= CROSSOVER_SELECTION_PROBABILITY())
            {
                testCases.add(parentBTests.get(i));
            }
        }
        return testCases;
    }

    static Set<TestCase> mutate(Set<TestCase> tests)
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
                if (RANDOM().nextDouble() <= MUTATION_PROBABILITY())
                {
                    double rand = RANDOM().nextDouble();
                    Object newInput;
                    double offset = RANDOM().nextGaussian();
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
