package com.github.hollandjake.com3529.generation.solver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.generation.TestCase;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Breed
{
    private static final Random RANDOM = new Random();
    public static List<MethodTestSuite> repopulate(List<MethodTestSuite> oldPopulation, int populationSize)
    {
        List<MethodTestSuite> newPopulation = new ArrayList<>(oldPopulation);

        for (int p = oldPopulation.size() - 1; p < populationSize; p++)
        {
            MethodTestSuite parentA = oldPopulation.get(RANDOM.nextInt(oldPopulation.size()));
            MethodTestSuite parentB = oldPopulation.get(RANDOM.nextInt(oldPopulation.size()));
            
            TestCase[] parentATests = parentA.getTests().toArray(new TestCase[0]);
            TestCase[] parentBTests = parentB.getTests().toArray(new TestCase[0]);
            int minSize = Math.min(parentATests.length, parentBTests.length);
            int maxSize = Math.max(parentATests.length, parentBTests.length);

            Set<TestCase> testCases = new HashSet<>();
            int size = minSize + (Math.abs(maxSize - minSize) > 0? RANDOM.nextInt(maxSize - minSize) : 0);

            Method method = parentA.getMethod();
            for (int i = 0; i < size; i++)
            {
                Object[] inputs = new Object[parentATests.length];
                for (int inputIndex = 0; inputIndex < parentATests.length; inputIndex++)
                {
                    inputs[inputIndex] = RANDOM.nextBoolean() ? parentATests[inputIndex] : parentBTests[inputIndex];
                }
                testCases.add(new TestCase(method, inputs));
            }
            MethodTestSuite newSuite = new MethodTestSuite(method, new HashSet<>(testCases));

            newPopulation.add(newSuite);
        }

        return newPopulation;
    }
}
