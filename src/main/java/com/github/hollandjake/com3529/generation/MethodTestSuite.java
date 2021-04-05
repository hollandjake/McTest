package com.github.hollandjake.com3529.generation;

import java.util.Objects;
import java.util.Set;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import lombok.Data;
import lombok.ToString;

@Data
public class MethodTestSuite
{
    @ToString.Exclude
    private final Method method;
    private final Set<TestCase> tests;
    private boolean executed = false;
    private CoverageReport coverageReport;

    public void execute()
    {
        if (!executed)
        {
            coverageReport = new CoverageReport(method.getMethodTree());

            tests.stream()
                 .map(test -> {
                     test.execute();
                     return test.getCoverageReport();
                 })
                 .filter(Objects::nonNull)
                 .forEach(cr -> coverageReport = coverageReport.join(cr));
            executed = true;
        }
    }

    public void build(ClassOrInterfaceDeclaration classDeclaration, String className, String methodName)
    {
        int testNumber = 0;
        for (TestCase test : tests) {
            MethodDeclaration methodDeclaration = classDeclaration.addMethod("test"+testNumber, Modifier.publicModifier().getKeyword());
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
