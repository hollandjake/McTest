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

public class IntegerInputMutatorTest
{
    private IntegerInputMutator mutator;

    @Before
    public void setUp()
    {
        setFinalStatic(InputMutator.class, "NUMBER_DISTRIBUTION", 100);
        mutator = new IntegerInputMutator();
    }

    @Test
    public void testGenerate()
    {
        Integer generated = mutator.generate();
        assertThat(generated, instanceOf(Integer.class));
        assertThat(generated, allOf(greaterThan(-100),lessThan(100)));
    }

    @Test
    public void testModify()
    {
        assertThat(mutator.modify(1, 1), equalTo(2));
        assertThat(mutator.modify(1, -1), equalTo(0));
        assertThat(mutator.modify(0, -1), equalTo(-1));
        assertThat(mutator.modify(100, 100), equalTo(200));
    }
}