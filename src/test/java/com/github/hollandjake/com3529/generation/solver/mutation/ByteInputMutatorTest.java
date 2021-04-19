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

public class ByteInputMutatorTest
{
    private MockedStatic<InputMutator> inputMutator;
    private ByteInputMutator mutator;

    @Before
    public void setUp()
    {
        inputMutator = mockInputMutator();
        mutator = new ByteInputMutator();
    }

    @After
    public void teardown()
    {
        inputMutator.close();
    }

    @Test
    public void testGenerate()
    {
        Byte generatedChar = mutator.generate();
        assertThat(generatedChar, instanceOf(Byte.class));
        assertThat(generatedChar, allOf(greaterThanOrEqualTo((byte) -100), lessThanOrEqualTo((byte) 100)));
    }

    @Test
    public void testModify()
    {
        assertThat(mutator.modify((byte) 1, 1), equalTo((byte) 2));
        assertThat(mutator.modify((byte) 1, -1), equalTo((byte) 0));
        assertThat(mutator.modify((byte) 0, -1), equalTo((byte) -1));
        assertThat(mutator.modify((byte) 100, 100), equalTo((byte) 200));
    }
}