package com.github.hollandjake.com3529;

import java.nio.file.Path;
import java.util.List;

import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.generation.solver.Breed;
import com.github.hollandjake.com3529.generation.solver.InitialPopulationGenerator;
import com.github.hollandjake.com3529.generation.solver.genetics.NaturalSelection;
import com.github.hollandjake.com3529.utils.FileTools;
import com.typesafe.config.ConfigFactory;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class MethodTestGenerator
{
    private static final int POPULATION_SIZE = ConfigFactory.load().getInt("Genetics.PopulationSize");
    private static final double TARGET_FITNESS = ConfigFactory.load().getDouble("Genetics.TargetFitness");
    private static final long MAX_ITERATIONS = ConfigFactory.load().getLong("Genetics.MaxIterations");

    @SneakyThrows
    public static void forMethod(Method method, String packageName, Path outputPath)
    {
        log.info("Generating tests for \"{}.{}.{}\"",
                 packageName,
                 method.getExecutableMethod().getDeclaringClass().getSimpleName(),
                 method.getExecutableMethod().getName());
        MethodTestSuite testSuite = generate(method);
        FileTools.generateJUnitTests(testSuite, packageName, outputPath);
    }

    private static MethodTestSuite generate(Method method)
    {
        List<MethodTestSuite> population = InitialPopulationGenerator.generate(method, POPULATION_SIZE);

        long start = System.currentTimeMillis();

        for (long i = 0; i < MAX_ITERATIONS; i++)
        {
            //Evaluate population
            population.parallelStream().forEach(MethodTestSuite::execute);

            //Select elites
            population = NaturalSelection.overPopulation(population);

            if (population.get(0).getFitness() <= TARGET_FITNESS)
            {
                log.debug("Execution time: " + (System.currentTimeMillis() - start));
                return population.get(0);
            }
            //Log fitness
            log.debug("Best Fitness: " + population.get(0).getFitness());

            //Breed
            population = Breed.repopulate(method, population, POPULATION_SIZE);
        }
        log.info("Failed to find a test suite matching the required fitness. Generating best found");

        log.debug("Execution time: " + (System.currentTimeMillis() - start));
        return population.get(0);
    }
}
