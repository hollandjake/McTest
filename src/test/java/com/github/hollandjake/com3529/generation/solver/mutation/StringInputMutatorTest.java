package com.github.hollandjake.com3529.generation.solver.mutation;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import static com.github.hollandjake.com3529.testutils.TestUtils.setFinalStatic;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;

public class StringInputMutatorTest
{
    private StringInputMutator mutator;

    @Before
    public void setUp()
    {
        setFinalStatic(InputMutator.class, "RANDOM", new Random(1));
        mutator = new StringInputMutator();
    }

    @Test
    public void testGenerate()
    {
        String generated = mutator.generate();
        assertThat(generated, instanceOf(String.class));
        assertThat(generated.length(), lessThan(50));
    }

    @Test
    public void testModify()
    {
        assertThat(mutator.modify("hello", 0), equalTo("hello"));
        assertThat(mutator.modify("hello", 1), equalTo("hello1"));
        assertThat(mutator.modify("hello", 2), equalTo("hell8o2"));
        assertThat(mutator.modify("hello", 10), equalTo("helflf"));
        assertThat(mutator.modify("", 10), equalTo(">J,?"));
    }
}