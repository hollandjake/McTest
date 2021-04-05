package com.github.hollandjake.com3529.generation.solver.genetics;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.github.hollandjake.com3529.generation.MethodTestSuite;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NaturalSelection
{
    private static final int TOP_N = 10;

    public static List<MethodTestSuite> overPopulation(List<MethodTestSuite> population)
    {
        return population
                .stream()
                .sorted(Comparator.comparingDouble(left -> left.getCoverageReport().getFitness()))
                .limit(TOP_N)
                .collect(Collectors.toList());
    }
}
