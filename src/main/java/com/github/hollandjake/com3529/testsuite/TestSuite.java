package com.github.hollandjake.com3529.testsuite;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import com.github.hollandjake.com3529.generation.CoverageReport;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.SimpleName;

import lombok.Data;

@Data
public class TestSuite
{
    private final Method method;
    private final File fileUnderTest;
    private final CoverageReport coverageReport;
    private final Set<Test> tests;

    public CompilationUnit generateTestSuite()
    {
        CompilationUnit cu = new CompilationUnit();
        Class<?> declaringClass = method.getDeclaringClass();
        cu.setPackageDeclaration(declaringClass.getPackage().getName());
        cu.addImport("org.junit.Test");
        cu.addImport("org.junit.Assert.assertEquals", true, false);
        ClassOrInterfaceDeclaration classDeclaration = cu.addClass(declaringClass.getSimpleName());

        AtomicInteger i = new AtomicInteger();
        tests.stream().parallel()
             .map(Test::generateTestCase)
             .map(test -> test.setName(new SimpleName("test" + i.getAndIncrement())))
             .forEach(classDeclaration::addMember);

        return cu;
    }
}
