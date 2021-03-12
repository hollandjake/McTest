package com3529;

import org.junit.Test;

public class TestGeneratorTest
{
    @Test
    public void shouldGenerateTest()
    {
        TestGenerator.of("Triangle.java");
    }
}