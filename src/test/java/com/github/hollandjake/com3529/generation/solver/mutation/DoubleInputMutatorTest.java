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

public class DoubleInputMutatorTest
{
    private DoubleInputMutator mutator;

    @Before
    public void setUp()
    {
        setFinalStatic(InputMutator.class, "NUMBER_DISTRIBUTION", 100);
        mutator = new DoubleInputMutator();
    }

    @Test
    public void testGenerate()
    {
        Double generated = mutator.generate();
        assertThat(generated, instanceOf(Double.class));
        assertThat(generated, allOf(greaterThan(-100d),lessThan(100d)));
    }

    @Test
    public void testModify()
    {
        assertThat(mutator.modify(1d, 1), equalTo(2d));
        assertThat(mutator.modify(1d, -1), equalTo(0d));
        assertThat(mutator.modify(0d, -1), equalTo(-1d));
        assertThat(mutator.modify(100d, 100), equalTo(200d));
    }
}