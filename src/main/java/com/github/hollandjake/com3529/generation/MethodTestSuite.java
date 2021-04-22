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

@Data
public class MethodTestSuite
{
    @ToString.Exclude
    private final Method method;
    @NonNull
    private Set<TestCase> tests;
    @Setter(AccessLevel.PACKAGE)
    private boolean executed = false;
    private CoverageReport coverageReport;

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

    public TestSuite finalise()
    {
        java.lang.reflect.Method method = this.method.getExecutableMethod();
        return new TestSuite(
                method,
                this.method.getFileUnderTest(),
                this.coverageReport,
                tests.stream()
                     .filter(testCase -> Objects.nonNull(testCase.getOutput()))
                     .map(TestCase::finalise)
                     .collect(Collectors.toSet())
        );
    }

    public double getFitness()
    {
        return Objects.requireNonNull(this.coverageReport).getFitness();
    }
}
