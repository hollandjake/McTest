package com.github.hollandjake.com3529.generation.solver.mutation;

import org.junit.Before;
import org.junit.Test;

import static com.github.hollandjake.com3529.testutils.TestUtils.setFinalStatic;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class FloatInputMutatorTest
{
    private FloatInputMutator mutator;

    @Before
    public void setUp()
    {
        setFinalStatic(InputMutator.class, "NUMBER_DISTRIBUTION", 100);
        mutator = new FloatInputMutator();
    }

    @Test
    public void testGenerate()
    {
        Float generated = mutator.generate();
        assertThat(generated, instanceOf(Float.class));
        assertThat(generated, allOf(greaterThan(-100f),lessThan(100f)));
    }

    @Test
    public void testModify()
    {
        assertThat(mutator.modify(1f, 1), equalTo(2f));
        assertThat(mutator.modify(1f, -1), equalTo(0f));
        assertThat(mutator.modify(0f, -1), equalTo(-1f));
        assertThat(mutator.modify(100f, 100), equalTo(200f));
    }
}