package com.github.hollandjake.com3529.generation.solver.mutation;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class BooleanInputMutatorTest
{
    private BooleanInputMutator mutator;

    @Before
    public void setUp()
    {
        mutator = new BooleanInputMutator();
    }

    @Test
    public void testGenerate()
    {
        assertThat(mutator.generate(), instanceOf(Boolean.class));
    }

    @Test
    public void testModify()
    {
        assertEquals(false, mutator.modify(true, 1));
        assertEquals(true, mutator.modify(false, 1));
        assertEquals(false, mutator.modify(false, 2));
        assertEquals(true, mutator.modify(true, 2));
    }
}