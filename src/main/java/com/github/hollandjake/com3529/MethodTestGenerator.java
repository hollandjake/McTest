package com.github.hollandjake.com3529;

import java.lang.reflect.Method;
import java.util.List;

import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.generation.solver.Breed;
import com.github.hollandjake.com3529.generation.solver.InitialPopulationGenerator;
import com.github.hollandjake.com3529.generation.solver.genetics.NaturalSelection;
import com.github.hollandjake.com3529.utils.tree.Tree;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MethodTestGenerator
{
    private static final int POPULATION_SIZE = 100;
    @SneakyThrows
    public static void forMethod(Method method, Tree methodTree)
    {
        MethodTestSuite testSuite = generate(method, methodTree);
        //TODO: Write to file
        System.out.println(testSuite);
    }

    private static MethodTestSuite generate(Method method, Tree methodTree) {
        List<MethodTestSuite> population = InitialPopulationGenerator.generate(method, methodTree, POPULATION_SIZE);

        while (true)
        {
            //Evalute population
            population.forEach(MethodTestSuite::execute);

            //Select elites
            population = NaturalSelection.overPopulation(population);

            //Termination condition
            if (population.get(0).getFitness() == 14) {
                break;
            } else {
                System.out.println(population.get(0).getFitness());
            }

            //Breed
            population = Breed.repopulate(method, population, POPULATION_SIZE);
        }

        return population.get(0);
    }
}
