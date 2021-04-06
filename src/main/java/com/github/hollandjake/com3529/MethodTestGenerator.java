package com.github.hollandjake.com3529;

import java.util.List;

import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.generation.solver.Breed;
import com.github.hollandjake.com3529.generation.solver.InitialPopulationGenerator;
import com.github.hollandjake.com3529.generation.solver.genetics.NaturalSelection;
import com.typesafe.config.ConfigFactory;

import com.github.hollandjake.com3529.utils.FileTools;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MethodTestGenerator
{
    private static final int POPULATION_SIZE = ConfigFactory.load().getInt("Genetics.PopulationSize");
    private static final double TARGET_FITNESS = ConfigFactory.load().getDouble("Genetics.TargetFitness");
    private static final double MAX_ITERATIONS = ConfigFactory.load().getDouble("Genetics.MaxIterations");

    @SneakyThrows
    public static void forMethod(Method method)
    {
        MethodTestSuite testSuite = generate(method);
        FileTools.generateJUnitTests(testSuite);
    }

    private static MethodTestSuite generate(Method method)
    {
        List<MethodTestSuite> population = InitialPopulationGenerator.generate(method, POPULATION_SIZE);

        long iterations = 0;
        do {
            //Evaluate population
            population.forEach(MethodTestSuite::execute);

            //Select elites
            population = NaturalSelection.overPopulation(population);

            //Log fitness
            System.out.println(population.get(0).getFitness());

            //Breed
            population = Breed.repopulate(method, population, POPULATION_SIZE);

        } while (++iterations < MAX_ITERATIONS && population.get(0).getFitness() > TARGET_FITNESS);

        return population.get(0);
    }
}
