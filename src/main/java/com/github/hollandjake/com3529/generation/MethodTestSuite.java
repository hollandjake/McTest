package com.github.hollandjake.com3529.generation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

@Data
public class MethodTestSuite
{
    @ToString.Exclude
    private final Method method;
    @NonNull
    private Set<TestCase> tests;
    private boolean executed = false;
    private CoverageReport coverageReport;

    public void execute()
    {
        if (!executed)
        {
            coverageReport = new CoverageReport(method.getMethodTree());

            tests.parallelStream().forEach(TestCase::execute);

            //Order testcases set by fitness
            List<TestCase> orderedTests = new ArrayList<>(tests);
            orderedTests.sort(Comparator.comparingDouble(left -> left.getCoverageReport().getFitness()));

            Set<TestCase> minimisedTests = new HashSet<>();
            Set<String> branchesCovered = new HashSet<>();
            for (TestCase testCase : orderedTests)
            {
                if (branchesCovered.addAll(testCase.getCoverageReport().getBranchesCovered()))
                {
                    //If it has increased the branches covered then add it
                    minimisedTests.add(testCase);
                }
            }
            tests = minimisedTests;

            for (TestCase testCase : tests)
            {
                coverageReport = coverageReport.join(testCase.getCoverageReport());
            }
            executed = true;
        }
    }

    public void build(ClassOrInterfaceDeclaration classDeclaration, String className, String methodName)
    {
        int testNumber = 0;
        for (TestCase test : tests)
        {
            MethodDeclaration methodDeclaration = classDeclaration.addMethod("test" + testNumber,
                                                                             Modifier.publicModifier().getKeyword());
            methodDeclaration.addAnnotation(StaticJavaParser.parseAnnotation("@Test"));
            test.build(methodDeclaration, className, methodName);
            testNumber++;
        }
    }

    public double getFitness()
    {
        return this.coverageReport.getFitness();
    }
}
