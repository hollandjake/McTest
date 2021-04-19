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

public class ShortInputMutatorTest
{
    private MockedStatic<InputMutator> inputMutator;
    private ShortInputMutator mutator;

    @Before
    public void setUp()
    {
        inputMutator = mockInputMutator();
        mutator = new ShortInputMutator();
    }

    @After
    public void teardown()
    {
        inputMutator.close();
    }

    @Test
    public void testGenerate()
    {
        Short generated = mutator.generate();
        assertThat(generated, instanceOf(Short.class));
        assertThat(generated, allOf(greaterThanOrEqualTo((short) -100), lessThanOrEqualTo((short) 100)));
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