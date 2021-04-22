package com.github.hollandjake.com3529.testsuite;

import java.lang.reflect.Method;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;

import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

public class TestTest
{
    @Test
    public void testGenerateTestCase()
    {
        Method mockMethod = mock(Method.class);
        Object[] inputs = new Object[] { '1', "1", (short) 1, (byte) 1, 1 };
        Object output = 1;
        com.github.hollandjake.com3529.testsuite.Test test = new com.github.hollandjake.com3529.testsuite.Test(
                mockMethod,
                inputs,
                output);

        when(mockMethod.getDeclaringClass()).thenReturn((Class) String.class);
        when(mockMethod.getName()).thenReturn("testName");

        MethodDeclaration methodDeclaration = test.generateTestCase();
        MethodDeclaration expected = StaticJavaParser.parseMethodDeclaration(
                "@Test\n" +
                        "public void test() {" +
                        "assertEquals(\"1\", String.valueOf(String.testName('1',\"1\",(short) 1, (byte) 1, 1)));" +
                        "}"
        );

        assertEquals(methodDeclaration.toString(), expected.toString());
    }
}