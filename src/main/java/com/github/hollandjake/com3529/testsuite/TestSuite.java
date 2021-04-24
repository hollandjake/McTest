package com.github.hollandjake.com3529.testsuite;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.hollandjake.com3529.generation.CoverageReport;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.SimpleName;

import lombok.Data;

/**
 * An unmodifiable {@link TestSuite} instance generated by {@link com.github.hollandjake.com3529.generation.MethodTestSuite}
 */
@Data
public class TestSuite
{
    /**
     * The modified method that was tested
     */
    private final Method method;
    /**
     * The file the method was created from
     */
    private final File fileUnderTest;
    /**
     * The package the file contained
     */
    private final String packageUnderTest;
    /**
     * The coverage report for the class
     */
    private final CoverageReport coverageReport;
    /**
     * The individual tests which are part of the test suite
     */
    private final Set<Test> tests;

    /**
     * Generate a JUnit test class representing the {@link TestSuite}
     *
     * @param testClassName The generated name of the test class
     * @return The {@link CompilationUnit} representing the JUnit test class
     */
    public CompilationUnit generateTestSuite(String testClassName)
    {
        CompilationUnit cu = new CompilationUnit();
        cu.setPackageDeclaration(packageUnderTest);
        cu.addImport("org.junit.Test");
        cu.addImport("org.junit.Assert.assertEquals", true, false);
        ClassOrInterfaceDeclaration classDeclaration = cu.addClass(testClassName);

        AtomicInteger i = new AtomicInteger();
        tests.stream().parallel()
             .map(Test::generateTestCase)
             .map(test -> test.setName(new SimpleName("test" + i.getAndIncrement())))
             .forEach(classDeclaration::addMember);

        return cu;
    }
}
