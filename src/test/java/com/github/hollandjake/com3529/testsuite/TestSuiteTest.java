package com.github.hollandjake.com3529.testsuite;

import java.lang.reflect.Method;
import java.util.Collections;

import com.github.hollandjake.com3529.generation.CoverageReport;

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
        com.github.hollandjake.com3529.testsuite.Test test = new com.github.hollandjake.com3529.testsuite.Test(mockMethod,new Object[]{1,2,3}, 1);
        TestSuite testSuite = new TestSuite(mockMethod, null, mockCoverage, Collections.singleton(test));

        when(mockMethod.getDeclaringClass()).thenReturn((Class) String.class);

        assertEquals(testSuite.generateTestSuite().toString(), "package java.lang;\n"
                + "\n"
                + "import org.junit.Test;\n"
                + "import static org.junit.Assert.assertEquals;\n"
                + "\n"
                + "public class String {\n"
                + "\n"
                + "    @Test\n"
                + "    public void test0() {\n"
                + "        assertEquals(\"1\", String.valueOf(String.null(1, 2, 3)));\n"
                + "    }\n"
                + "}\n");
    }
}