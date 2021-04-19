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

public class ByteInputMutatorTest
{
    private ByteInputMutator mutator;

    @Before
    public void setUp()
    {
        setFinalStatic(InputMutator.class, "NUMBER_DISTRIBUTION", 100);
        mutator = new ByteInputMutator();
    }

    @Test
    public void testGenerate()
    {
        Byte generatedChar = mutator.generate();
        assertThat(generatedChar, instanceOf(Byte.class));
        assertThat(generatedChar, allOf(greaterThan((byte) -100), lessThan((byte) 100)));
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