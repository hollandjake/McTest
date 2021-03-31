package com.github.hollandjake.com3529.generation.solver;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.github.hollandjake.com3529.generation.MethodTestSuite;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ElitismGenerator
{
    private static final int TOP_N = 10;

    public static List<MethodTestSuite> onPopulation(List<MethodTestSuite> population)
    {
        List<MethodTestSuite> sorted = population
                .stream()
                .sorted(Comparator.comparingInt(left -> left.getCoverageReport().getTotalBranches()))
                .collect(Collectors.toList());

        return sorted.subList(0, TOP_N);
    }
}
