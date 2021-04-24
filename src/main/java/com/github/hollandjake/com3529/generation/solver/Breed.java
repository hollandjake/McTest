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

/**
 * Responsible for creating the new individuals for a population
 */
@UtilityClass
public class Breed
{
    @Accessors(fluent = true)
    @Getter(value = AccessLevel.PACKAGE,
            lazy = true)
    private static final Random RANDOM = new SecureRandom();

    /**
     * The probability an individual's gene will be carried into a new child
     */
    @Accessors(fluent = true)
    @Getter(value = AccessLevel.PACKAGE,
            lazy = true)
    private static final double CROSSOVER_SELECTION_PROBABILITY = ConfigFactory.load().getDouble(
            "Genetics.CrossoverSelectionProbability");

    /**
     * The probability a gene will mutate
     */
    @Accessors(fluent = true)
    @Getter(value = AccessLevel.PACKAGE,
            lazy = true)
    private static final double MUTATION_PROBABILITY = ConfigFactory.load().getDouble("Genetics.MutationProbability");

    /**
     * Repopulate the population back to the size we want, this includes the old parents.
     *
     * @param method         The method that the test generation is running on
     * @param oldPopulation  The population that will be used as parents for the new population
     * @param populationSize How large the new population should be
     * @return The new population
     */
    public static List<MethodTestSuite> repopulate(Method method,
            List<MethodTestSuite> oldPopulation,
            int populationSize)
    {
        List<MethodTestSuite> tests = new ArrayList<>(oldPopulation);

        // Generate all the new children for the population by picking two random parents and performing crossover and mutation
        tests.addAll(IntStream.range(oldPopulation.size(), populationSize).parallel().mapToObj(p -> {
            MethodTestSuite parentA = oldPopulation.get(RANDOM().nextInt(oldPopulation.size()));
            MethodTestSuite parentB = oldPopulation.get(RANDOM().nextInt(oldPopulation.size()));

            Set<TestCase> testCases = mutate(crossover(parentA.getTests(), parentB.getTests()));

            return new MethodTestSuite(method, testCases);
        }).collect(Collectors.toList()));

        // Limit is applied here as the old population could be larger than the target population size
        return tests.stream().limit(populationSize).collect(Collectors.toList());
    }

    /**
     * Creates a new Set of {@link TestCase TestCases} which are a cross-join subset of the two parents
     * The selection process is based on {@link #CROSSOVER_SELECTION_PROBABILITY}
     *
     * @param parentA The first parent's tests
     * @param parentB The second parent's tests
     * @return a new Set of {@link TestCase TestCases}
     */
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

    /**
     * Mutate a Set of {@link TestCase TestCases} through a series of mutation operations upon the inputs of each {@link TestCase}
     * <p>
     * Possible mutations are as follows:
     * <ul>
     *     <li>Gaussian movement using the <code>InputMutator.add</code>. (This can be both negative and positive)
     *     <li>Random new input used
     * </ul>
     *
     * These mutations are not equality weighted - Gaussian movement has a 2/3 chance while Random has a 1/3 chance.
     * The chance an input is even mutated is defined by the {@link #MUTATION_PROBABILITY} property
     *
     * @param tests The set of input {@link TestCase TestCases}
     * @return The mutated set of input {@link TestCase TestCases}
     */
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
