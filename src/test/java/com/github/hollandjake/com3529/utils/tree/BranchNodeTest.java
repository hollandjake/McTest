package com.github.hollandjake.com3529.utils.tree;

import com.github.hollandjake.com3529.generation.ConditionCoverage;
import com.github.javaparser.ast.expr.BinaryExpr;

import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertNull;

public class BranchNodeTest
{
    @Test
    public void testGetFitnessWithTruthiness()
    {
        BranchNode mockParent = mock(BranchNode.class);
        BranchNode branchNode = new BranchNode(mockParent, 1);
        ConditionNode mockCondition = mock(ConditionNode.class);
        ConditionCoverage mockConditionCoverage = mock(ConditionCoverage.class);

        branchNode.addCondition(mockCondition);

        when(mockCondition.getConditionCoverage()).thenReturn(mockConditionCoverage, mockConditionCoverage, null);
        when(mockConditionCoverage.getTruthDistance()).thenReturn(1d);
        when(mockConditionCoverage.getFalseDistance()).thenReturn(0d);
        when(mockParent.getFitness(anyBoolean())).thenReturn(1d);

        assertEquals(0.5d, branchNode.getFitness(true));
        assertEquals(0d, branchNode.getFitness(false));
        assertEquals(2d, branchNode.getFitness(false));
    }

    @Test
    public void testGetFitness()
    {
        BranchNode mockParent = mock(BranchNode.class);
        BranchNode branchNode = new BranchNode(mockParent, 1);
        ConditionNode mockCondition = mock(ConditionNode.class);
        ConditionCoverage mockConditionCoverage = mock(ConditionCoverage.class);

        when(mockCondition.getConditionCoverage()).thenReturn(mockConditionCoverage, (ConditionCoverage) null);

        branchNode.addCondition(mockCondition);

        when(mockConditionCoverage.getNormalisedFitness()).thenReturn(1d);
        when(mockParent.getFitness()).thenReturn(1d);

        assertEquals(1d, branchNode.getFitness());
        assertEquals(2d, branchNode.getFitness());
    }

    @Test
    public void testGetRawFitness()
    {
        BranchNode branchNode = new BranchNode(null, 1);
        ConditionNode mockCondition = mock(ConditionNode.class);
        branchNode.addCondition(mockCondition);
        when(mockCondition.getFitness()).thenReturn(1d);

        assertEquals(1d, branchNode.getRawFitness());
    }

    @Test
    public void testGetConditionNode()
    {
        BranchNode branchNode = new BranchNode(null, 1);
        ConditionNode mockCondition = mock(ConditionNode.class);

        branchNode.addCondition(mockCondition);
        when(mockCondition.getConditionId()).thenReturn(1);

        assertEquals(mockCondition, branchNode.getConditionNode(1));
    }

    @Test
    public void testGetConditionNodeWithNoMatch()
    {
        BranchNode branchNode = new BranchNode(null, 1);
        ConditionNode mockCondition = mock(ConditionNode.class);
        branchNode.addCondition(mockCondition);
        when(mockCondition.getConditionId()).thenReturn(0);

        assertNull(branchNode.getConditionNode(1));
    }

    @Test
    public void testClone()
    {
        Tree mockTree = mock(Tree.class);
        BranchNode branchNode = new BranchNode(1, mockTree, false);
        BranchNode childBranchNodeThen = mock(BranchNode.class);
        BranchNode childBranchNodeElse = mock(BranchNode.class);

        ConditionNode mockCondition = mock(ConditionNode.class);
        branchNode.addCondition(mockCondition);
        branchNode.addConditionOperator(BinaryExpr.Operator.OR);

        branchNode.addThenChild(childBranchNodeThen);
        branchNode.addElseChild(childBranchNodeElse);

        when(childBranchNodeThen.clone()).thenReturn(childBranchNodeThen);
        when(childBranchNodeElse.clone()).thenReturn(childBranchNodeElse);
        when(mockCondition.clone()).thenReturn(mockCondition);

        BranchNode clone = branchNode.clone();
        clone.setParentNode(mockTree);

        assertEquals(branchNode, clone);
        assertNotSame(branchNode, clone);
    }

    @Test
    public void testGetTotalConditionCoverageWithNullCoverage()
    {
        BranchNode branchNode = new BranchNode(1, null, false);
        ConditionNode mockCondition = mock(ConditionNode.class);
        branchNode.addCondition(mockCondition);

        when(mockCondition.getConditionCoverage()).thenReturn(null);

        assertNull(branchNode.getTotalConditionCoverage());
    }

    @Test
    public void testGetTotalConditionCoverageWithOneCondition()
    {
        BranchNode branchNode = new BranchNode(1, null, false);
        ConditionNode mockCondition = mock(ConditionNode.class);
        ConditionCoverage mockCoverage = mock(ConditionCoverage.class);
        branchNode.addCondition(mockCondition);

        when(mockCondition.getConditionCoverage()).thenReturn(mockCoverage);

        assertEquals(mockCoverage, branchNode.getTotalConditionCoverage());
    }

    @Test
    public void testGetTotalConditionCoverageWithMultipleConditions()
    {
        BranchNode branchNode = new BranchNode(1, null, false);
        ConditionNode mockCondition = mock(ConditionNode.class);
        branchNode.addCondition(mockCondition);
        branchNode.addConditionOperator(BinaryExpr.Operator.AND);
        branchNode.addCondition(mockCondition);
        branchNode.addConditionOperator(BinaryExpr.Operator.OR);
        branchNode.addCondition(mockCondition);
        ConditionCoverage coverage1 = new ConditionCoverage(0, true, 2d, 1d);
        ConditionCoverage coverage2 = new ConditionCoverage(1, true, 1d, 2d);
        ConditionCoverage coverage3 = new ConditionCoverage(2, false, 5d, 5d);
        ConditionCoverage expected = new ConditionCoverage(0, null, 6d, 6d);

        when(mockCondition.getConditionCoverage()).thenReturn(coverage1, coverage2, coverage3);

        assertEquals(expected, branchNode.getTotalConditionCoverage());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testGetTotalConditionCoverageThrowsUnsupportedException()
    {
        BranchNode branchNode = new BranchNode(1, null, false);
        ConditionNode mockCondition = mock(ConditionNode.class);
        branchNode.addCondition(mockCondition);
        branchNode.addConditionOperator(BinaryExpr.Operator.DIVIDE);
        branchNode.addCondition(mockCondition);
        ConditionCoverage coverage1 = new ConditionCoverage(0, true, 2d, 1d);
        ConditionCoverage coverage2 = new ConditionCoverage(1, true, 1d, 2d);

        when(mockCondition.getConditionCoverage()).thenReturn(coverage1, coverage2);

        branchNode.getTotalConditionCoverage();
    }

    @Test
    public void testGetIfNode()
    {
        BranchNode branchNode = new BranchNode(null, 1);
        BranchNode childNode = new BranchNode(null, 2);
        branchNode.addThenChild(childNode);

        assertEquals(branchNode, branchNode.getBranchNode(1));
        assertEquals(childNode, branchNode.getBranchNode(2));
    }

    @Test
    public void testJoin()
    {
        Tree mockParent = mock(Tree.class);
        BranchNode branchNode = new BranchNode(mockParent, 1);
        BranchNode childNodeThen = mock(BranchNode.class);
        BranchNode childNodeElse = mock(BranchNode.class);
        ConditionNode mockCondition = mock(ConditionNode.class);

        branchNode.addThenChild(childNodeThen);
        branchNode.addElseChild(childNodeElse);
        branchNode.addCondition(mockCondition);
        branchNode.addConditionOperator(BinaryExpr.Operator.OR);

        when(mockParent.getBranchNode(anyInt())).thenReturn(branchNode, childNodeThen, childNodeElse);
        when(mockCondition.join(any())).thenReturn(mockCondition);
        when(childNodeThen.join(any())).thenReturn(childNodeThen);
        when(childNodeElse.join(any())).thenReturn(childNodeElse);

        BranchNode join = branchNode.join(mockParent);
        join.setParentNode(mockParent);
        assertEquals(branchNode, join);
        assertNotSame(branchNode, join);
    }

    @Test
    public void testJoinWithNullOther()
    {
        Tree mockTree = mock(Tree.class);
        BranchNode branchNode = new BranchNode(null, 1);
        assertEquals(branchNode, branchNode.join(null));

        BranchNode join = branchNode.join(mockTree);
        assertEquals(branchNode, join);
        assertNotSame(branchNode, join);
    }
}