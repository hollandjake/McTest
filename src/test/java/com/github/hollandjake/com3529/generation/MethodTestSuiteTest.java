package com.github.hollandjake.com3529.generation;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.github.hollandjake.com3529.testsuite.TestSuite;
import com.github.hollandjake.com3529.utils.tree.Tree;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class MethodTestSuiteTest
{
    private Method mockMethod;
    private TestCase mockTest1;
    private TestCase mockTest2;
    private TestCase mockTest3;
    private Set<TestCase> mockTests;
    private MethodTestSuite methodTestSuite;

    @BeforeMethod
    public void setUp()
    {
        mockMethod = mock(Method.class);
        mockTest1 = mock(TestCase.class);
        mockTest2 = mock(TestCase.class);
        mockTest3 = mock(TestCase.class);
        mockTests = new HashSet<>(Arrays.asList(mockTest1, mockTest2, mockTest3));
        methodTestSuite = new MethodTestSuite(mockMethod, mockTests);
    }

    @Test
    public void testExecute()
    {
        CoverageReport mockCoverageReport = mock(CoverageReport.class);
        Tree treeMock = mock(Tree.class);

        when(mockMethod.getMethodTree()).thenReturn(treeMock);
        when(mockTest1.isExecuted()).thenReturn(true);
        when(mockTest2.isExecuted()).thenReturn(true);
        when(mockTest3.isExecuted()).thenReturn(false);
        when(mockTest1.getCoverageReport()).thenReturn(mockCoverageReport);
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
    public void testFinalise()
    {
        java.lang.reflect.Method mockMethodExecutable = mock(java.lang.reflect.Method.class);
        File mockFile = mock(File.class);
        when(mockMethod.getExecutableMethod()).thenReturn(mockMethodExecutable);
        when(mockMethod.getFileUnderTest()).thenReturn(mockFile);
        CoverageReport mockCoverage = mock(CoverageReport.class);

        methodTestSuite.setCoverageReport(mockCoverage);

        Object[] inputs1 = { 1 };
        Object[] inputs3 = { 3 };
        Object output1 = "test1";
        Object output3 = "test3";

        when(mockTest1.getInputs()).thenReturn(inputs1);
        when(mockTest1.getOutput()).thenReturn(output1);
        com.github.hollandjake.com3529.testsuite.Test test1Mapped = new com.github.hollandjake.com3529.testsuite.Test(
                mockMethodExecutable,
                inputs1,
                output1
        );
        when(mockTest1.finalise()).thenReturn(test1Mapped);

        when(mockTest2.getOutput()).thenReturn(null);

        when(mockTest3.getInputs()).thenReturn(inputs3);
        when(mockTest3.getOutput()).thenReturn(output3);
        com.github.hollandjake.com3529.testsuite.Test test3Mapped = new com.github.hollandjake.com3529.testsuite.Test(
                mockMethodExecutable,
                inputs3,
                output3
        );
        when(mockTest3.finalise()).thenReturn(test3Mapped);

        TestSuite generatedTestSuite = methodTestSuite.finalise();
        TestSuite expected = new TestSuite(
                mockMethodExecutable,
                mockFile,
                mockCoverage,
                new HashSet<>(Arrays.asList(test3Mapped, test1Mapped))
        );
        assertEquals(generatedTestSuite, expected);
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