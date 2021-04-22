package com.github.hollandjake.com3529.generation;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.github.hollandjake.com3529.utils.tree.Tree;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class MethodTestSuiteTest
{
    private Method mockMethod;
    private TestCase mockTest;
    private TestCase mockTest2;
    private TestCase mockTest3;
    private Set<TestCase> mockTests;
    private MethodTestSuite methodTestSuite;

    @BeforeMethod
    public void setUp()
    {
        mockMethod = mock(Method.class);
        mockTest = mock(TestCase.class);
        mockTest2 = mock(TestCase.class);
        mockTest3 = mock(TestCase.class);
        mockTests = new HashSet<>(Arrays.asList(mockTest, mockTest2, mockTest3));
        methodTestSuite = new MethodTestSuite(mockMethod, mockTests);
    }

    @Test
    public void testExecute()
    {
        CoverageReport mockCoverageReport = mock(CoverageReport.class);
        Tree treeMock = mock(Tree.class);

        when(mockMethod.getMethodTree()).thenReturn(treeMock);
        when(mockTest.isExecuted()).thenReturn(true);
        when(mockTest2.isExecuted()).thenReturn(true);
        when(mockTest3.isExecuted()).thenReturn(false);
        when(mockTest.getCoverageReport()).thenReturn(mockCoverageReport);
        when(mockTest2.getCoverageReport()).thenReturn(mockCoverageReport);
        when(mockCoverageReport.getFitness()).thenReturn(1d, 2d);
        when(mockCoverageReport.getBranchesCovered()).thenReturn(Collections.singleton("0f"));

        when(mockCoverageReport.join(any())).thenReturn(mockCoverageReport);

        methodTestSuite.execute();
        assertTrue(methodTestSuite.isExecuted());
    }

    @Test
    public void testExecuteNoExecuteWhenAlreadyExecuted()
    {
        methodTestSuite.setExecuted(true);
        methodTestSuite.execute();
        assertTrue(methodTestSuite.isExecuted());
        assertNull(methodTestSuite.getCoverageReport());
    }

    @Test
    public void testBuild()
    {
        ClassOrInterfaceDeclaration mockClass = mock(ClassOrInterfaceDeclaration.class);
        MethodDeclaration mockMethodDeclaration = mock(MethodDeclaration.class);
        when(mockTest.getOutput()).thenReturn("testOutput");
        when(mockTest2.getOutput()).thenReturn(null);
        when(mockClass.addMethod(anyString(),any())).thenReturn(mockMethodDeclaration);
        when(mockMethodDeclaration.addAnnotation(any(AnnotationExpr.class))).thenReturn(mockMethodDeclaration);

        methodTestSuite.build(mockClass, "testClassName", "testMethodName");

        verify(mockClass, times(1)).addMethod(any(),any());
    }

    @Test
    public void testGetFitness()
    {
        CoverageReport mockCoverageReport = mock(CoverageReport.class);
        methodTestSuite.setCoverageReport(mockCoverageReport);
        when(mockCoverageReport.getFitness()).thenReturn(1d);

        assertEquals(1d, methodTestSuite.getFitness());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testGetFitnessThrowsNullPointerWhenNoCoverageReport()
    {
        methodTestSuite.getFitness();
    }
}