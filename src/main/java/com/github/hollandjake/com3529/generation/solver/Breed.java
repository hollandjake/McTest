package com.github.hollandjake.com3529.generation.solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.generation.TestCase;
import com.typesafe.config.ConfigFactory;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Breed
{
    private static final Random RANDOM = new Random(ConfigFactory.load().getInt("Genetics.Seed.Breed"));
    private static final double CROSSOVER_PROBABILITY = ConfigFactory.load().getDouble("Genetics.CrossoverProbability");

    public static List<MethodTestSuite> repopulate(Method method,
            List<MethodTestSuite> oldPopulation,
            int populationSize)
    {
        List<MethodTestSuite> newPopulation = new ArrayList<>(oldPopulation);

        for (int p = oldPopulation.size(); p < populationSize; p++)
        {
            MethodTestSuite parentA = oldPopulation.get(RANDOM.nextInt(oldPopulation.size()));
            MethodTestSuite parentB = oldPopulation.get(RANDOM.nextInt(oldPopulation.size()));

            TestCase[] parentATests = parentA.getTests().toArray(new TestCase[0]);
            TestCase[] parentBTests = parentB.getTests().toArray(new TestCase[0]);

            Set<TestCase> testCases = mutate(crossover(parentATests, parentBTests));

            MethodTestSuite newSuite = new MethodTestSuite(method, testCases);

            newPopulation.add(newSuite);
        }

        return newPopulation;
    }

    private static Set<TestCase> crossover(TestCase[] parentA, TestCase[] parentB)
    {
        int maxTests = Math.min(parentA.length, parentB.length);

        Set<TestCase> testCases = new HashSet<>();

        for (int i = 0; i < maxTests; i++)
        {
            if (RANDOM.nextDouble() <= CROSSOVER_PROBABILITY)
            {
                testCases.add(parentB[i]);
            }
            else
            {
                testCases.add(parentA[i]);
            }
        }
        return testCases;
    }

    private static Set<TestCase> mutate(Set<TestCase> tests)
    {
        //TODO: Implement mutation
        return tests;
    }
}
