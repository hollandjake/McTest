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

public class LongInputMutatorTest
{
    private LongInputMutator mutator;

    @Before
    public void setUp()
    {
        setFinalStatic(InputMutator.class, "NUMBER_DISTRIBUTION", 100);
        mutator = new LongInputMutator();
    }

    @Test
    public void testGenerate()
    {
        Long generated = mutator.generate();
        assertThat(generated, instanceOf(Long.class));
        assertThat(generated, allOf(greaterThan(-100L),lessThan(100L)));
    }

    @Test
    public void testModify()
    {
        assertThat(mutator.modify(1L, 1), equalTo(2L));
        assertThat(mutator.modify(1L, -1), equalTo(0L));
        assertThat(mutator.modify(0L, -1), equalTo(-1L));
        assertThat(mutator.modify(100L, 100), equalTo(200L));
    }
}