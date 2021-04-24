package com.github.hollandjake.com3529.generation;

import com.github.hollandjake.com3529.utils.tree.Tree;
import com.github.javaparser.ast.body.MethodDeclaration;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import lombok.SneakyThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertSame;

public class TestCaseTest
{
    private Method methodMock;

    @BeforeMethod
    public void setUp()
    {
        methodMock = mock(Method.class);
    }

    @Test
    @SneakyThrows
    public void testExecute()
    {
        Tree treeMock = mock(Tree.class);

        TestCase testCase = new TestCase(methodMock, new Object[] { 'c', "str", (short) 3, (byte) 4, 5 }, false, null, null);

        java.lang.reflect.Method mockMethod = mock(java.lang.reflect.Method.class);
        when(methodMock.getExecutableMethod()).thenReturn(mockMethod);
        when(methodMock.getMethodTree()).thenReturn(treeMock);
        when(treeMock.clone()).thenReturn(treeMock);
        when(mockMethod.invoke(any(), any())).thenReturn("testOutput");
        when(mockMethod.getDeclaringClass()).thenReturn((Class) String.class);

        testCase.execute();
        assertTrue(testCase.isExecuted());
        assertEquals(testCase.getOutput(), "testOutput");
    }

    @Test
    @SneakyThrows
    public void testExecuteHandlesException()
    {
        Tree treeMock = mock(Tree.class);

        TestCase testCase = new TestCase(methodMock, new Object[] { 'c', "str", (short) 3, (byte) 4, 5 }, false, null, null);

        java.lang.reflect.Method mockMethod = mock(java.lang.reflect.Method.class);
        when(methodMock.getExecutableMethod()).thenReturn(mockMethod);
        when(methodMock.getMethodTree()).thenReturn(treeMock);
        when(treeMock.clone()).thenReturn(treeMock);
        String failureMessage = "failureMessage";
        when(mockMethod.invoke(any(), any())).thenThrow(new UnsupportedOperationException(failureMessage));
        when(mockMethod.getDeclaringClass()).thenReturn((Class) String.class);

        testCase.execute();
        assertFalse(testCase.isExecuted());
        assertEquals(testCase.getOutput(), "java.lang.UnsupportedOperationException: "+failureMessage);
    }

    @Test
    public void testExecuteSkipRunningIfAlreadyExecuted()
    {
        TestCase testCase = new TestCase(methodMock, new Object[] { 'c', "str", (short) 3, (byte) 4, 5 }, true, null, null);
        testCase.execute();
        assertTrue(testCase.isExecuted());
        assertNull(testCase.getOutput());
    }
}