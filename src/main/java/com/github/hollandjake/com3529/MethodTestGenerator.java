package com.github.hollandjake.com3529;

import java.lang.reflect.Method;
import java.util.List;

import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.generation.solver.Breed;
import com.github.hollandjake.com3529.generation.solver.ElitismGenerator;
import com.github.hollandjake.com3529.generation.solver.InitialPopulationGenerator;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MethodTestGenerator
{
    private static final int POPULATION_SIZE = 100;
    @SneakyThrows
    public static void forMethod(Method method)
    {
        MethodTestSuite testSuite = generate(method);
        //TODO: Write to file
        System.out.println(testSuite);
    }

    private static MethodTestSuite generate(Method method) {
        List<MethodTestSuite> population = InitialPopulationGenerator.generate(method, POPULATION_SIZE);

        while (true)
        {
            //Evalute population
            population.forEach(MethodTestSuite::execute);

            //Select elites
            population = ElitismGenerator.onPopulation(population);

            //Termination condition
            if (population.get(0).getCoverageReport().getTotalBranches() == 14) {
                break;
            } else {
                System.out.println(population.get(0).getCoverageReport().getTotalBranches());
            }

            //Breed
            population = Breed.repopulate(method, population, POPULATION_SIZE);
        }

        return population.get(0);
    }
}
