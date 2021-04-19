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

public class ShortInputMutatorTest
{
    private ShortInputMutator mutator;

    @Before
    public void setUp()
    {
        setFinalStatic(InputMutator.class, "NUMBER_DISTRIBUTION", 100);
        mutator = new ShortInputMutator();
    }

    @Test
    public void testGenerate()
    {
        Short generated = mutator.generate();
        assertThat(generated, instanceOf(Short.class));
        assertThat(generated, allOf(greaterThan((short) -100), lessThan((short) 100)));
    }

    @Test
    public void testModify()
    {
        assertThat(mutator.modify((short) 1, 1), equalTo((short) 2));
        assertThat(mutator.modify((short) 1, -1), equalTo((short) 0));
        assertThat(mutator.modify((short) 0, -1), equalTo((short) -1));
        assertThat(mutator.modify((short) 100, 100), equalTo((short) 200));
    }
}