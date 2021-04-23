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
//public class DoubleInputMutatorTest
//{
//    private MockedStatic<InputMutator> inputMutator;
//    private DoubleInputMutator mutator;
//
//    @BeforeMethod
//    public void setUp()
//    {
//        inputMutator = mockInputMutator();
//        mutator = new DoubleInputMutator();
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
//        Double generated = mutator.generate();
//        assertThat(generated, instanceOf(Double.class));
//        assertThat(generated, allOf(greaterThanOrEqualTo(-100d),lessThanOrEqualTo(100d)));
//    }
//
//    @Test
//    public void testModify()
//    {
//        assertThat(mutator.modify(1d, 1), equalTo(2d));
//        assertThat(mutator.modify(1d, -1), equalTo(0d));
//        assertThat(mutator.modify(0d, -1), equalTo(-1d));
//        assertThat(mutator.modify(100d, 100), equalTo(200d));
//    }
//}