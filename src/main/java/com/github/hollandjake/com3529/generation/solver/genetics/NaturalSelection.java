package com.github.hollandjake.com3529.generation.solver.genetics;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.typesafe.config.ConfigFactory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

/**
 * Class responsible for handling the selection of the individuals who should progress to the next generation
 */
@UtilityClass
public class NaturalSelection
{
    /**
     * How many individuals to bring into the next generation from the previous generation
     */
    @Accessors(fluent = true)
    @Getter(value = AccessLevel.PACKAGE)
    private static final int TOP_N = ConfigFactory.load().getInt("Genetics.TopN");

    /**
     * Return the {@link #TOP_N} individuals sorted by ascending fitness
     *
     * i.e. Select the {@link #TOP_N} fittest individuals
     *
     * @param population The current population
     * @return A subset of the population who are the fittest {@link #TOP_N} individuals
     */
    public static List<MethodTestSuite> overPopulation(List<MethodTestSuite> population)
    {
        return population
                .stream()
                .sorted(Comparator.comparingDouble(MethodTestSuite::getFitness))
                .limit(TOP_N())
                .collect(Collectors.toList());
    }
}
