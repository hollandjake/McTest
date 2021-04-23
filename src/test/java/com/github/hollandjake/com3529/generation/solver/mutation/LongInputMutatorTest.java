//package com.github.hollandjake.com3529.generation.solver.mutation;
//
//import org.mockito.MockedStatic;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import static com.github.hollandjake.com3529.testutils.TestUtils.mockInputMutator;
//import static org.hamcrest.CoreMatchers.allOf;
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.CoreMatchers.instanceOf;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.greaterThanOrEqualTo;
//import static org.hamcrest.Matchers.lessThanOrEqualTo;
//
//public class LongInputMutatorTest
//{
//    private MockedStatic<InputMutator> inputMutator;
//    private LongInputMutator mutator;
//
//    @BeforeMethod
//    public void setUp()
//    {
//        inputMutator = mockInputMutator();
//        mutator = new LongInputMutator();
//    }
//
//    @AfterMethod
//    public void tearDown()
//    {
//        inputMutator.close();
//    }
//
//    @Test
//    public void testGenerate()
//    {
//        Long generated = mutator.generate();
//        assertThat(generated, instanceOf(Long.class));
//        assertThat(generated, allOf(greaterThanOrEqualTo(-100L),lessThanOrEqualTo(100L)));
//    }
//
//    @Test
//    public void testModify()
//    {
//        assertThat(mutator.modify(1L, 1), equalTo(2L));
//        assertThat(mutator.modify(1L, -1), equalTo(0L));
//        assertThat(mutator.modify(0L, -1), equalTo(-1L));
//        assertThat(mutator.modify(100L, 100), equalTo(200L));
//    }
//}