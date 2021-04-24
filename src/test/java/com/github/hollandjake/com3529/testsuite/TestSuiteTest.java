package com.github.hollandjake.com3529.testsuite;

import java.lang.reflect.Method;
import java.util.Collections;

import com.github.hollandjake.com3529.generation.CoverageReport;
import com.github.javaparser.StaticJavaParser;

import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

public class TestSuiteTest
{
    @Test
    public void testGenerateTestSuite()
    {
        Method mockMethod = mock(Method.class);
        CoverageReport mockCoverage = mock(CoverageReport.class);
        com.github.hollandjake.com3529.testsuite.Test test = new com.github.hollandjake.com3529.testsuite.Test(
                mockMethod,
                new Object[] { 1, 2, 3 },
                1);
        TestSuite testSuite = new TestSuite(mockMethod, null, "java.lang", mockCoverage, Collections.singleton(test));

        when(mockMethod.getDeclaringClass()).thenReturn((Class) String.class);
        when(mockMethod.getName()).thenReturn("mockMethod");

        assertEquals(StaticJavaParser.parse("package java.lang;"
                                                    + "import org.junit.Test;"
                                                    + "import static org.junit.Assert.assertEquals;"
                                                    + "public class StringMockMethodTest {"
                                                    + "    @Test public void test0() {"
                                                    + "        assertEquals(\"1\", String.valueOf(String.mockMethod(1, 2, 3)));"
                                                    + "    }"
                                                    + "}").toString(),
                     testSuite.generateTestSuite("StringMockMethodTest").toString());
    }
}