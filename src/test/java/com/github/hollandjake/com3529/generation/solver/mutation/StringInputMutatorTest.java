//package com.github.hollandjake.com3529.generation.solver.mutation;
//
//import org.mockito.MockedStatic;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import static com.github.hollandjake.com3529.testutils.TestUtils.mockInputMutator;
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.CoreMatchers.instanceOf;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.lessThanOrEqualTo;
//
//public class StringInputMutatorTest
//{
//    private MockedStatic<InputMutator> inputMutator;
//    private StringInputMutator mutator;
//
//    @BeforeMethod
//    public void setUp()
//    {
//        inputMutator = mockInputMutator();
//        mutator = new StringInputMutator();
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
//        String generated = mutator.generate();
//        assertThat(generated, instanceOf(String.class));
//        assertThat(generated.length(), lessThanOrEqualTo(50));
//    }
//
//    @Test
//    public void testModify()
//    {
//        assertThat(mutator.modify("hello", 0), equalTo("hello"));
//        assertThat(mutator.modify("hello", 1), equalTo("hello1"));
//        assertThat(mutator.modify("hello", 2), equalTo("hell8o2"));
//        assertThat(mutator.modify("hello", 10), equalTo("helflf"));
//        assertThat(mutator.modify("", 10), equalTo(">J,?"));
//    }
//}