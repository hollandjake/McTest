package com.github.hollandjake.com3529.generation.solver;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.generation.TestCase;

import org.mockito.MockedStatic;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.oneOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

public class BreedTest
{
    private MockedStatic<Breed> breed;

    @BeforeMethod
    public void setUp()
    {
        breed = mockStatic(Breed.class);
        Random random = new Random(1);
        breed.when(Breed::RANDOM).thenReturn(random);
        breed.when(Breed::CROSSOVER_SELECTION_PROBABILITY).thenReturn(0.75);
        breed.when(Breed::MUTATION_PROBABILITY).thenReturn(0.1);
        breed.when(() -> Breed.repopulate(any(), anyList(), anyInt())).thenCallRealMethod();
        breed.when(() -> Breed.crossover(any(), any())).thenCallRealMethod();
        breed.when(() -> Breed.mutate(anySet())).thenCallRealMethod();
    }

    @AfterMethod
    public void tearDown()
    {
        breed.close();
    }

    @Test
    public void testCrossover()
    {
        TestCase testCase1 = mock(TestCase.class);
        TestCase testCase2 = mock(TestCase.class);
        TestCase testCase3 = mock(TestCase.class);
        TestCase testCase4 = mock(TestCase.class);
        Set<TestCase> parentATests = new HashSet<>(Arrays.asList(testCase1, testCase2, testCase3,testCase4));
        Set<TestCase> parentBTests = new HashSet<>(Arrays.asList(testCase1, testCase2, testCase3));

        breed.when(Breed::CROSSOVER_SELECTION_PROBABILITY).thenReturn(0.5);

        Set<TestCase> outputs = Breed.crossover(parentATests, parentBTests);
        assertThat(outputs, hasItem(oneOf(testCase1, testCase2, testCase3, testCase4)));

        breed.when(Breed::CROSSOVER_SELECTION_PROBABILITY).thenReturn(1d);

        outputs = Breed.crossover(parentATests, parentBTests);
        assertThat(outputs, equalTo(new HashSet<>(Arrays.asList(testCase1, testCase2, testCase3, testCase4))));
    }

    @Test
    public void testCrossoverParentAShorter()
    {
        TestCase testCase1 = mock(TestCase.class);
        TestCase testCase2 = mock(TestCase.class);
        TestCase testCase3 = mock(TestCase.class);
        TestCase testCase4 = mock(TestCase.class);
        Set<TestCase> parentATests = new HashSet<>(Arrays.asList(testCase1, testCase2, testCase3));
        Set<TestCase> parentBTests = new HashSet<>(Arrays.asList(testCase1, testCase2, testCase3, testCase4));

        breed.when(Breed::CROSSOVER_SELECTION_PROBABILITY).thenReturn(0.5);

        Set<TestCase> outputs = Breed.crossover(parentATests, parentBTests);
        assertThat(outputs, hasItem(oneOf(testCase1, testCase2, testCase3, testCase4)));

        breed.when(Breed::CROSSOVER_SELECTION_PROBABILITY).thenReturn(1d);

        outputs = Breed.crossover(parentATests, parentBTests);
        assertThat(outputs, equalTo(new HashSet<>(Arrays.asList(testCase1, testCase2, testCase3, testCase4))));
    }

    @Test
    public void testMutate()
    {
        breed.when(Breed::MUTATION_PROBABILITY).thenReturn(0.5);
        TestCase testCase = mock(TestCase.class);
        when(testCase.getInputs()).thenReturn(new Object[]{1,2,3});
        Set<TestCase> outputs = Breed.mutate(Collections.singleton(testCase));
        assertThat(outputs, not(equalTo(Collections.singleton(testCase))));

        breed.when(Breed::MUTATION_PROBABILITY).thenReturn(0d);
        outputs = Breed.mutate(Collections.singleton(testCase));
        assertThat(outputs, equalTo(Collections.singleton(testCase)));
    }

    @Test
    public void testRepopulate()
    {
        int populationSize = 3;
        Method mockMethod = mock(Method.class);
        MethodTestSuite testSuite1 = mock(MethodTestSuite.class);
        MethodTestSuite testSuite2 = mock(MethodTestSuite.class);
        List<MethodTestSuite> oldPopulation = Arrays.asList(testSuite1, testSuite2);

        List<MethodTestSuite> output = Breed.repopulate(mockMethod, oldPopulation, populationSize);

        assertThat(output, hasSize(populationSize));
        assertThat(output, hasItems(testSuite1, testSuite2));
    }

    @Test
    public void testRepopulateWithPopulationSizeSmallerThanOldPopulation()
    {
        int populationSize = 1;
        Method mockMethod = mock(Method.class);
        MethodTestSuite testSuite1 = mock(MethodTestSuite.class);
        MethodTestSuite testSuite2 = mock(MethodTestSuite.class);
        List<MethodTestSuite> oldPopulation = Arrays.asList(testSuite1, testSuite2);

        List<MethodTestSuite> output = Breed.repopulate(mockMethod, oldPopulation, populationSize);

        assertThat(output, hasSize(populationSize));
        assertThat(output, either(hasItem(testSuite1)).or(hasItem(testSuite2)));
        breed.verify(never(), () -> Breed.crossover(any(),any()));
    }
}