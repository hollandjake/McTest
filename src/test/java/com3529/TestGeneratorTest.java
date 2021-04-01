package com3529;

import com.github.hollandjake.com3529.ClassTestGenerator;
import com.github.hollandjake.com3529.TestSuiteGenerator;

import org.junit.Test;

public class TestGeneratorTest
{
    @Test
    public void shouldGenerateTest()
    {
        TestSuiteGenerator.forPackage("com.github.hollandjake.test");
    }
    @Test
    public void shouldGenerateTestClass()
    {
        ClassTestGenerator.forClass("Triangle");
    }
}