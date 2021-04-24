package com.github.hollandjake.com3529.utils.tree;

import com.github.hollandjake.com3529.generation.ConditionCoverage;
import com.github.javaparser.Range;

import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

public class ConditionNodeTest
{
    @Test
    public void testGetFitness()
    {
        ConditionCoverage mockCoverage = mock(ConditionCoverage.class);
        ConditionNode conditionNode = new ConditionNode(mock(BranchNode.class),
                                                        0,
                                                        mockCoverage,
                                                        "testConditionString",
                                                        mock(Range.class));

        when(mockCoverage.getNormalisedFitness()).thenReturn(1d);

        assertEquals(1d, conditionNode.getFitness());
    }

    @Test
    public void testGetFitnessWithNullCoverage()
    {
        BranchNode mockParent = mock(BranchNode.class);
        ConditionNode conditionNode = new ConditionNode(mockParent, 0, null, "testConditionString", mock(Range.class));

        when(mockParent.getFitness()).thenReturn(1d);

        assertEquals(2d, conditionNode.getFitness());
    }

    @Test
    public void testTestClone()
    {
        BranchNode mockParent = mock(BranchNode.class);
        ConditionCoverage mockCoverage = mock(ConditionCoverage.class);
        Range mockRange = mock(Range.class);

        ConditionNode conditionNode = new ConditionNode(mockParent, 1, mockCoverage, "testConditionString", mockRange);

        when(mockCoverage.clone()).thenReturn(mockCoverage);


        ConditionNode clone = conditionNode.clone();
        clone.setParent(mockParent);
        assertEquals(clone, conditionNode);
        assertNotSame(clone, conditionNode);

        conditionNode = new ConditionNode(mockParent, 1, null, "testConditionString", mockRange);

        clone = conditionNode.clone();
        clone.setParent(mockParent);
        assertEquals(clone, conditionNode);
        assertNotSame(clone, conditionNode);
    }

    @Test
    public void testJoin()
    {
        BranchNode mockParent1 = mock(BranchNode.class);
        BranchNode mockParent2 = mock(BranchNode.class);
        ConditionCoverage mockCoverage1 = mock(ConditionCoverage.class);
        ConditionCoverage mockCoverage2 = mock(ConditionCoverage.class);
        ConditionCoverage mockCoverage3 = mock(ConditionCoverage.class);
        Range mockRange = mock(Range.class);
        Tree mockTree = mock(Tree.class);

        ConditionNode conditionNodeA = new ConditionNode(mockParent1, 0, mockCoverage1, "testConditionString", mockRange);
        ConditionNode conditionNodeB = new ConditionNode(mockParent2, 0, mockCoverage2, "testConditionString", mockRange);

        when(mockTree.getConditionNode(anyInt())).thenReturn(conditionNodeB);
        when(mockCoverage1.join(any())).thenReturn(mockCoverage3);

        ConditionNode expected = new ConditionNode(mockParent1, 0, mockCoverage3, "testConditionString", mockRange);
        assertEquals(conditionNodeA.join(mockTree), expected);
    }

    @Test
    public void testJoinOtherNull()
    {
        BranchNode mockParent1 = mock(BranchNode.class);
        ConditionCoverage mockCoverage = mock(ConditionCoverage.class);
        Range mockRange = mock(Range.class);
        Tree mockTree = mock(Tree.class);

        ConditionNode conditionNodeA = new ConditionNode(mockParent1, 0, mockCoverage, "testConditionString", mockRange);
        ConditionNode spy = spy(conditionNodeA);
        when(mockTree.getConditionNode(anyInt())).thenReturn(null);
        when(mockCoverage.clone()).thenReturn(mockCoverage);
        assertEquals(spy.join(mockTree), conditionNodeA);
        verify(spy, times(1)).clone();
    }
}