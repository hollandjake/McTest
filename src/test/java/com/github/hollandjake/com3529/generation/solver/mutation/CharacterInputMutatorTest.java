package com.github.hollandjake.com3529.generation.solver.mutation;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class CharacterInputMutatorTest
{
    private CharacterInputMutator mutator;

    @Before
    public void setUp()
    {
        mutator = new CharacterInputMutator();
    }

    @Test
    public void testGenerate()
    {
        Character generated = mutator.generate();
        assertThat(generated, instanceOf(Character.class));
        assertThat((int) generated, allOf(greaterThan(32),lessThan(128)));
    }

    @Test
    public void testModify()
    {
        assertThat(mutator.modify('a', 1), equalTo('b'));
        assertThat(mutator.modify((char) 126, 1), equalTo((char) 32));
    }
}