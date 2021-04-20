package com.github.hollandjake.com3529.generation.solver.mutation;

import org.mockito.MockedStatic;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.github.hollandjake.com3529.testutils.TestUtils.mockInputMutator;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class FloatInputMutatorTest
{
    private MockedStatic<InputMutator> inputMutator;
    private FloatInputMutator mutator;

    @BeforeMethod
    public void setUp()
    {
        inputMutator = mockInputMutator();
        mutator = new FloatInputMutator();
    }

    @AfterMethod
    public void tearDown()
    {
        inputMutator.close();
    }

    @Test
    public void testGenerate()
    {
        Float generated = mutator.generate();
        assertThat(generated, instanceOf(Float.class));
        assertThat(generated, allOf(greaterThanOrEqualTo(-100f),lessThanOrEqualTo(100f)));
    }

    @Test
    public void testModify()
    {
        assertThat(mutator.modify(1f, 1), equalTo(2f));
        assertThat(mutator.modify(1f, -1), equalTo(0f));
        assertThat(mutator.modify(0f, -1), equalTo(-1f));
        assertThat(mutator.modify(100f, 100), equalTo(200f));
    }
}