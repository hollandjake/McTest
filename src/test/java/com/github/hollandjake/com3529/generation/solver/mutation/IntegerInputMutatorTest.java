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
//public class IntegerInputMutatorTest
//{
//    private MockedStatic<InputMutator> inputMutator;
//    private IntegerInputMutator mutator;
//
//    @BeforeMethod
//    public void setUp()
//    {
//        inputMutator = mockInputMutator();
//        mutator = new IntegerInputMutator();
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
//        Integer generated = mutator.generate();
//        assertThat(generated, instanceOf(Integer.class));
//        assertThat(generated, allOf(greaterThanOrEqualTo(-100),lessThanOrEqualTo(100)));
//    }
//
//    @Test
//    public void testModify()
//    {
//        assertThat(mutator.modify(1, 1), equalTo(2));
//        assertThat(mutator.modify(1, -1), equalTo(0));
//        assertThat(mutator.modify(0, -1), equalTo(-1));
//        assertThat(mutator.modify(100, 100), equalTo(200));
//    }
//}