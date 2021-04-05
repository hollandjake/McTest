package com.github.hollandjake.com3529;

import java.util.List;

import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.generation.solver.Breed;
import com.github.hollandjake.com3529.generation.solver.InitialPopulationGenerator;
import com.github.hollandjake.com3529.generation.solver.genetics.NaturalSelection;

import com.github.hollandjake.com3529.utils.WriteToFile;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
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

        //Auto create JUnit tests
        //Create test file with Java Parser
        String className = method.getExecutableMethod().getDeclaringClass().getSimpleName();
        String methodName = method.getExecutableMethod().getName();
        String testClassName = className+methodName.substring(0, 1).toUpperCase() + methodName.substring(1)+"Tests";

        CompilationUnit cu = new CompilationUnit();
        cu.setPackageDeclaration("com.github.hollandjake.com3529.test");
        cu.addImport("org.junit.Test");
        cu.addImport("org.junit.Assert.assertEquals",true,false);
        ClassOrInterfaceDeclaration classDeclaration = cu.addClass(testClassName);

        testSuite.build(classDeclaration, className, methodName);

        //Write file to the test location
        String fileLocation = String.format("../generatedTests/src/test/java/com/github/hollandjake/com3529/test/%s.java",testClassName);
        WriteToFile.writeToFile(fileLocation,cu.toString());

        System.out.println(cu.toString());
    }

    private static MethodTestSuite generate(Method method) {
        List<MethodTestSuite> population = InitialPopulationGenerator.generate(method, POPULATION_SIZE);

        while (true)
        {
            //Evalute population
            population.forEach(MethodTestSuite::execute);

            //Select elites
            population = NaturalSelection.overPopulation(population);

            //Termination condition
            if (population.get(0).getFitness() < 10) {
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
