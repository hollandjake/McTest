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
        return population
                .stream()
                .sorted(Comparator.comparingInt(left -> left.getCoverageReport().getTotalBranches()))
                .limit(TOP_N)
                .collect(Collectors.toList());
    }
}
