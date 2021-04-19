package com.github.hollandjake.com3529.generation.solver.mutation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import static com.github.hollandjake.com3529.testutils.TestUtils.mockInputMutator;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class LongInputMutatorTest
{
    private MockedStatic<InputMutator> inputMutator;
    private LongInputMutator mutator;

    @Before
    public void setUp()
    {
        inputMutator = mockInputMutator();
        mutator = new LongInputMutator();
    }

    @After
    public void teardown()
    {
        inputMutator.close();
    }

    @Test
    public void testGenerate()
    {
        Long generated = mutator.generate();
        assertThat(generated, instanceOf(Long.class));
        assertThat(generated, allOf(greaterThanOrEqualTo(-100L),lessThanOrEqualTo(100L)));
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