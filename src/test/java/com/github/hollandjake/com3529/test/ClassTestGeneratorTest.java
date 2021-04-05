package com.github.hollandjake.com3529.test;

import com.github.hollandjake.com3529.ClassTestGenerator;

import org.junit.Test;

public class ClassTestGeneratorTest
{
    @Test
    public void shouldGenerateTestClass()
    {
        ClassTestGenerator.forClass("Triangle");
    }
}