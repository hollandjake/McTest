package com.github.hollandjake.com3529.generation;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    public void build(ClassOrInterfaceDeclaration classDeclaration, String className, String methodName)
    {
        int testNumber = 0;
        for (TestCase test : tests)
        {
            if (test.getOutput() != null)
            {
                MethodDeclaration methodDeclaration = classDeclaration.addMethod("test" + testNumber,
                                                                                 Modifier.publicModifier()
                                                                                         .getKeyword());
                methodDeclaration.addAnnotation(StaticJavaParser.parseAnnotation("@Test"));
                test.build(methodDeclaration, className, methodName);
                testNumber++;
            }
        }
    }

    public double getFitness()
    {
        return Objects.requireNonNull(this.coverageReport).getFitness();
    }
}
