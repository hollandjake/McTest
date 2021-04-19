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

public class IntegerInputMutatorTest
{
    private MockedStatic<InputMutator> inputMutator;
    private IntegerInputMutator mutator;

    @Before
    public void setUp()
    {
        inputMutator = mockInputMutator();
        mutator = new IntegerInputMutator();
    }

    @After
    public void teardown()
    {
        inputMutator.close();
    }

    @Test
    public void testGenerate()
    {
        Integer generated = mutator.generate();
        assertThat(generated, instanceOf(Integer.class));
        assertThat(generated, allOf(greaterThanOrEqualTo(-100),lessThanOrEqualTo(100)));
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