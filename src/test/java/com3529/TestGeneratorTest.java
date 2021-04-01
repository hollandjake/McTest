package com3529;

import com.github.hollandjake.com3529.ClassTestGenerator;

import org.junit.Test;

public class TestGeneratorTest
{
    @Test
    public void shouldGenerateTestClass()
    {
        ClassTestGenerator.forClass("Triangle");
    }
}