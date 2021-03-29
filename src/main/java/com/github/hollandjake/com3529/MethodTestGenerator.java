package com.github.hollandjake.com3529;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import com.github.hollandjake.com3529.genetics.TestScenario;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MethodTestGenerator
{
    private static final Random random = new Random();

    @SneakyThrows
    public static void forMethod(Method method)
    {
        //TODO: Convert method into valid format
        List<TestScenario> solutions = solve(method);

        System.out.println(solutions);
    }

    private static List<TestScenario> solve(Method method)
    {
        int populationSize = 100;
        List<TestScenario> population = generateInitialPopulation(method, populationSize);

        List<TestScenario> bestScenarios = new ArrayList<>();

        for (int p = 0; p < method.getParameterCount(); p++)
        {
            TestScenario truthyScenario;
            do
            {
                List<TestScenario> topN = rank(Objects.requireNonNull(population), p, 10);
                truthyScenario = topN.get(0);

                //Minimisation
                if (truthyScenario.getGym().getFitnesses().get(p).getResult())
                {
                    break;
                }
                population = breed(method, populationSize, topN);
            }
            while (true);
            bestScenarios.add(truthyScenario);
        }

        return bestScenarios;
    }

    private static List<TestScenario> generateInitialPopulation(Method method, Integer populationSize)
    {
        List<TestScenario> population = new ArrayList<>();
        Class<?>[] methodParameterTypes = method.getParameterTypes();

        for (int i = 0; i < populationSize; i++)
        {
            List<Object> inputs = new ArrayList<>();
            //Skip last one as that is the Gym object
            for (int j = 0; j < methodParameterTypes.length - 1; j++)
            {
                inputs.add(generateRandomInput(methodParameterTypes[j]));
            }
            population.add(TestScenario.forMethod(method, inputs));
        }

        return population;
    }

    private static Object generateRandomInput(Class<?> type)
    {
        if (type == Integer.class || type == int.class)
        {
            return random.nextInt() % 100;
        }
        else if (type == Float.class || type == float.class)
        {
            return random.nextFloat() % 100;
        }
        else if (type == Double.class || type == double.class)
        {
            return random.nextDouble() % 100;
        }
        else if (type == Long.class || type == long.class)
        {
            return random.nextLong() % 100;
        }
        else
        {
            throw new RuntimeException("Class type <" + type + "> not supported");
        }
    }

    private static List<TestScenario> rank(List<TestScenario> population,
            Integer fitnessIndex,
            Integer topN)
    {
        List<TestScenario> ordered = population.stream().sorted((a, b) -> {
            Float aScore = a.getGym().getFitnesses().get(fitnessIndex).getTruthScore();
            Float bScore = b.getGym().getFitnesses().get(fitnessIndex).getTruthScore();

            return aScore.compareTo(bScore);
        }).collect(Collectors.toList());

        return ordered.subList(0, topN);
    }

    private static List<TestScenario> breed(Method method, Integer populationSize, List<TestScenario> parents)
    {
        List<TestScenario> nextGeneration = new ArrayList<>(parents);

        for (int i = parents.size() - 1; i < populationSize; i++)
        {
            TestScenario parentA = parents.get(random.nextInt(parents.size()));
            TestScenario parentB = parents.get(random.nextInt(parents.size()));

            List<Object> inputs = new ArrayList<>(parentA.getInputs());
            for (int j = 0; j < inputs.size(); j++)
            {
                double mutation = random.nextGaussian() * 100;
                if (random.nextBoolean())
                {
                    inputs.set(j, (Integer) parentB.getInputs().get(j) + (int) mutation);
                }
                else
                {
                    inputs.set(j, (Integer) inputs.get(j) + (int) mutation);
                }
            }

            nextGeneration.add(TestScenario.forMethod(method, inputs));
        }
        return nextGeneration;
    }
}
