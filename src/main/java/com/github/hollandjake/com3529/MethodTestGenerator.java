package com.github.hollandjake.com3529;

import java.io.File;
import java.util.List;

import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.generation.solver.Breed;
import com.github.hollandjake.com3529.generation.solver.InitialPopulationGenerator;
import com.github.hollandjake.com3529.generation.solver.genetics.NaturalSelection;
import com.github.hollandjake.com3529.testsuite.TestSuite;
import com.github.hollandjake.com3529.utils.FileTools;
import com.typesafe.config.ConfigFactory;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Responsible for generating the tests for an individual method
 */
@Slf4j
@UtilityClass
public class MethodTestGenerator
{
    /**
     * The size of the population for each iteration
     */
    @Getter
    @Accessors(fluent = true)
    private static final int POPULATION_SIZE = ConfigFactory.load().getInt("Genetics.PopulationSize");

    /**
     * The target fitness the system should achieve 0 representing all conditions covered, 1 representing no conditions covered
     */
    @Getter
    @Accessors(fluent = true)
    private static final double TARGET_FITNESS = ConfigFactory.load().getDouble("Genetics.TargetFitness");

    /**
     * Maximum iterations before the program terminates before finding a solution
     */
    @Getter
    @Accessors(fluent = true)
    private static final long MAX_ITERATIONS = ConfigFactory.load().getLong("Genetics.MaxIterations");

    /**
     * Generate the {@link TestSuite} for the method, writing the results optionally to the output directory
     *
     * @param method          The method under test
     * @param packageName     The package name of the method under test
     * @param outputDirectory The directory to output to
     * @return The generated {@link TestSuite} for the method
     */
    @SneakyThrows
    public static TestSuite forMethod(Method method, String packageName, File outputDirectory)
    {
        log.info(
                "Generating tests for \"{}.{}.{}\"",
                packageName,
                method.getExecutableMethod().getDeclaringClass().getSimpleName(),
                method.getExecutableMethod().getName()
        );

        TestSuite testSuite = generate(method);

        if (outputDirectory != null)
        {
            FileTools.generateJUnitTests(testSuite, packageName, outputDirectory);
            FileTools.generateCoverageReport(testSuite, outputDirectory);
        }
        return testSuite;
    }

    /**
     * Generate a {@link MethodTestSuite} through an evolutionary algorithm to generate tests for the method
     *
     * @param method The method under test
     * @return The generated {@link TestSuite} for the method
     */
    private static TestSuite generate(Method method)
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
                return population.get(0).finalise();
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
        return population.get(0).finalise();
    }
}
