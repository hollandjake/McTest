package com.github.hollandjake.com3529.test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TriangleClassifyTest
{
    @Test
    public void test0() {
        assertEquals("INVALID", String.valueOf(Triangle.classify(1, 2, 3)));
    }
}