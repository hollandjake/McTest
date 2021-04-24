package com.github.hollandjake.com3529.generation;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.hollandjake.com3529.testsuite.Test;
import com.github.hollandjake.com3529.testsuite.TestSuite;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Container representing an individual in the population
 */
@Data
public class MethodTestSuite
{
    /**
     * The method under test
     */
    @ToString.Exclude
    private final Method method;
    /**
     * A set of {@link TestCase TestCases} representing the genome of this individual
     */
    @NonNull
    private Set<TestCase> tests;
    @Setter(AccessLevel.PACKAGE)
    private boolean executed = false;
    private CoverageReport coverageReport;

    /**
     * If the {@link MethodTestSuite} has yet to be run and evaluated this will
     * execute the method storing all the coverage information
     * inside {@link #coverageReport}
     */
    public void execute()
    {
        if (!executed)
        {
            coverageReport = new CoverageReport(method.getMethodTree());

            Set<TestCase> minimisedTests = new HashSet<>();
            Set<String> branchesCovered = new HashSet<>();

            //Order testcases set by fitness
            tests.parallelStream()
                 .filter(testCase -> {
                     testCase.execute();
                     return testCase.isExecuted();
                 })
                 .sorted(Comparator.comparingDouble(left -> left.getCoverageReport().getFitness()))
                 .forEachOrdered(testCase -> {
                     if (branchesCovered.addAll(testCase.getCoverageReport().getBranchesCovered()))
                     {
                         //If it has increased the branches covered then add it
                         minimisedTests.add(testCase);
                     }
                 });

            tests = minimisedTests;

            tests.stream()
                 .map(TestCase::getCoverageReport)
                 .reduce(CoverageReport::join)
                 .ifPresent(x -> coverageReport = x);

            executed = true;
        }
    }

    /**
     * Produce the finalised version of a {@link MethodTestSuite} in which none of the parameters can be modified
     *
     * @return Unmodifiable {@link TestSuite}
     */
    public TestSuite finalise()
    {
        java.lang.reflect.Method method = this.method.getExecutableMethod();
        return new TestSuite(
                method,
                this.method.getFileUnderTest(),
                this.method.getPackageUnderTest(),
                this.coverageReport,
                tests.stream()
                     .filter(testCase -> Objects.nonNull(testCase.getOutput()))
                     .map(TestCase::finalise)
                     .collect(Collectors.toSet())
        );
    }

    /**
     * Returns the fitness of the coverage report
     *
     * @return The fitness of the coverage report
     * @throws NullPointerException if there is no coverage report
     */
    public double getFitness()
    {
        return Objects.requireNonNull(this.coverageReport).getFitness();
    }
}
