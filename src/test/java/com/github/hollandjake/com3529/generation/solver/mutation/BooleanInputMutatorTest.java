package com.github.hollandjake.com3529.generation.solver.mutation;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class BooleanInputMutatorTest
{
    private BooleanInputMutator mutator;

    @BeforeMethod
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
        assertFalse(mutator.modify(true, 1));
        assertTrue(mutator.modify(false, 1));
        assertFalse(mutator.modify(false, 2));
        assertTrue(mutator.modify(true, 2));
    }
}