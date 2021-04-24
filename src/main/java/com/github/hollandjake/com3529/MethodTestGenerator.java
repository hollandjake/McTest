package com.github.hollandjake.com3529;

import java.io.File;
import java.util.List;

import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.generation.solver.Breed;
import com.github.hollandjake.com3529.generation.solver.InitialPopulationGenerator;
import com.github.hollandjake.com3529.generation.solver.genetics.NaturalSelection;
import com.github.hollandjake.com3529.testsuite.Test;
import com.github.hollandjake.com3529.testsuite.TestSuite;
import com.github.hollandjake.com3529.utils.FileTools;
import com.typesafe.config.ConfigFactory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class MethodTestGenerator
{
    @Getter
    @Accessors(fluent = true)
    private static final int POPULATION_SIZE = ConfigFactory.load().getInt("Genetics.PopulationSize");

    @Getter
    @Accessors(fluent = true)
    private static final double TARGET_FITNESS = ConfigFactory.load().getDouble("Genetics.TargetFitness");

    @Getter
    @Accessors(fluent = true)
    private static final long MAX_ITERATIONS = ConfigFactory.load().getLong("Genetics.MaxIterations");

    @SneakyThrows
    public static TestSuite forMethod(Method method, String packageName, File outputDirectory)
    {
        log.info("Generating tests for \"{}.{}.{}\"",
                 packageName,
                 method.getExecutableMethod().getDeclaringClass().getSimpleName(),
                 method.getExecutableMethod().getName());
        TestSuite testSuite = generate(method).finalise();

        if (outputDirectory != null)
        {
            FileTools.generateJUnitTests(testSuite, packageName, outputDirectory);
            FileTools.generateCoverageReport(testSuite, outputDirectory);
        }
        return testSuite;
    }

    private static MethodTestSuite generate(Method method)
    {
        List<MethodTestSuite> population = InitialPopulationGenerator.generate(method, POPULATION_SIZE());

        long start = System.currentTimeMillis();

        for (long i = 1; i < MAX_ITERATIONS() + 1; i++)
        {
            // Evaluate population
            population.forEach(MethodTestSuite::execute);

            // Select elites
            population = NaturalSelection.overPopulation(population);

            double bestFitness = population.isEmpty() ? Double.MAX_VALUE : population.get(0).getFitness();

            // Log fitness
            log.debug("[Iteration: {}/{}] Best Fitness: {}", i, MAX_ITERATIONS(), bestFitness);

            if (bestFitness <= TARGET_FITNESS())
            {
                log.debug("Execution time: {}ms", System.currentTimeMillis() - start);
                return population.get(0);
            }
            else if (i == MAX_ITERATIONS())
            {
                break;
            }

            // Breed
            population = Breed.repopulate(method, population, POPULATION_SIZE());
        }
        log.info(
                "Failed to find a test suite matching the required fitness ({}). Generating best found",
                TARGET_FITNESS()
        );

        log.debug("Execution time: {}ms", System.currentTimeMillis() - start);
        return population.get(0);
    }
}
