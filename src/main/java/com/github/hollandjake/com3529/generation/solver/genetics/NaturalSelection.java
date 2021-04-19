package com.github.hollandjake.com3529.generation.solver.genetics;

import java.util.Comparator;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;

import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.typesafe.config.ConfigFactory;

import lombok.AccessLevel;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NaturalSelection
{
    private static final int TOP_N = ConfigFactory.load().getInt("Genetics.TopN");

    public static List<MethodTestSuite> overPopulation(List<MethodTestSuite> population)
    {
        return population
                .stream()
                .sorted(Comparator.comparingDouble(MethodTestSuite::getFitness))
                .limit(TOP_N())
                .collect(Collectors.toList());
    }

    public static int TOP_N()
    {
        return NaturalSelection.TOP_N;
    }
}
