package com.github.hollandjake.com3529.generation.solver.mutation;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class InputMutatorTest
{
    @Test
    public void testGenerate()
    {
        assertThat(InputMutator.generate(Integer.class), instanceOf(Integer.class));
        assertThat(InputMutator.generate(Float.class), instanceOf(Float.class));
        assertThat(InputMutator.generate(Double.class), instanceOf(Double.class));
        assertThat(InputMutator.generate(Long.class), instanceOf(Long.class));
        assertThat(InputMutator.generate(Byte.class), instanceOf(Byte.class));
        assertThat(InputMutator.generate(Short.class), instanceOf(Short.class));
        assertThat(InputMutator.generate(Boolean.class), instanceOf(Boolean.class));
        assertThat(InputMutator.generate(Character.class), instanceOf(Character.class));
        assertThat(InputMutator.generate(String.class), instanceOf(String.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGenerateThrowsException()
    {
        InputMutator.generate(Object.class);
    }

    @Test
    public void testAdd()
    {
        assertThat(InputMutator.add(1, 1), instanceOf(Integer.class));
        assertThat(InputMutator.add((Integer) 1, 1), instanceOf(Integer.class));
        assertThat(InputMutator.add(1f, 1), instanceOf(Float.class));
        assertThat(InputMutator.add(1F, 1), instanceOf(Float.class));
        assertThat(InputMutator.add(1d, 1), instanceOf(Double.class));
        assertThat(InputMutator.add(1D, 1), instanceOf(Double.class));
        assertThat(InputMutator.add((long) 1, 1), instanceOf(Long.class));
        assertThat(InputMutator.add(1L, 1), instanceOf(Long.class));
        assertThat(InputMutator.add((byte) 1, 1), instanceOf(Byte.class));
        assertThat(InputMutator.add((short) 1, 1), instanceOf(Short.class));
        assertThat(InputMutator.add(true, 1), instanceOf(Boolean.class));
        assertThat(InputMutator.add('a', 1), instanceOf(Character.class));
        assertThat(InputMutator.add("hello", 1), instanceOf(String.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddThrowsException()
    {
        InputMutator.add(new Object(), 1);
    }
}