//package com.github.hollandjake.com3529.utils.tree;
//
//import com.github.hollandjake.com3529.generation.ConditionCoverage;
//import com.github.javaparser.ast.expr.BinaryExpr;
//
//import org.testng.annotations.Test;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyBoolean;
//import static org.mockito.ArgumentMatchers.anyByte;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import static org.testng.AssertJUnit.assertEquals;
//import static org.testng.AssertJUnit.assertNotSame;
//import static org.testng.AssertJUnit.assertNull;
//
//public class IfNodeTest
//{
//    @Test
//    public void testGetFitnessWithTruthiness()
//    {
//        IfNode mockParent = mock(IfNode.class);
//        IfNode ifNode = new IfNode(mockParent, 1);
//        ConditionNode mockCondition = mock(ConditionNode.class);
//        ConditionCoverage mockConditionCoverage = mock(ConditionCoverage.class);
//
//        ifNode.addCondition(mockCondition);
//
//        when(mockCondition.getConditionCoverage()).thenReturn(mockConditionCoverage, mockConditionCoverage, null);
//        when(mockConditionCoverage.getTruthDistance()).thenReturn(1d);
//        when(mockConditionCoverage.getFalseDistance()).thenReturn(0d);
//        when(mockParent.getFitness(anyBoolean())).thenReturn(1d);
//
//        assertEquals(0.5d, ifNode.getFitness(true));
//        assertEquals(0d, ifNode.getFitness(false));
//        assertEquals(2d, ifNode.getFitness(false));
//    }
//
//    @Test
//    public void testGetFitness()
//    {
//        IfNode mockParent = mock(IfNode.class);
//        IfNode ifNode = new IfNode(mockParent, 1);
//        ConditionNode mockCondition = mock(ConditionNode.class);
//        ConditionCoverage mockConditionCoverage = mock(ConditionCoverage.class);
//
//        when(mockCondition.getConditionCoverage()).thenReturn(mockConditionCoverage, (ConditionCoverage) null);
//
//        ifNode.addCondition(mockCondition);
//
//        when(mockConditionCoverage.getNormalisedFitness()).thenReturn(1d);
//        when(mockParent.getFitness()).thenReturn(1d);
//
//        assertEquals(1d, ifNode.getFitness());
//        assertEquals(2d, ifNode.getFitness());
//    }
//
//    @Test
//    public void testGetRawFitness()
//    {
//        IfNode ifNode = new IfNode(null, 1);
//        ConditionNode mockCondition = mock(ConditionNode.class);
//        ifNode.addCondition(mockCondition);
//        when(mockCondition.getFitness()).thenReturn(1d);
//
//        assertEquals(1d, ifNode.getRawFitness());
//    }
//
//    @Test
//    public void testGetConditionNode()
//    {
//        IfNode ifNode = new IfNode(null, 1);
//        ConditionNode mockCondition = mock(ConditionNode.class);
//
//        ifNode.addCondition(mockCondition);
//        when(mockCondition.getConditionId()).thenReturn(1);
//
//        assertEquals(mockCondition, ifNode.getConditionNode(1));
//    }
//
//    @Test
//    public void testGetConditionNodeWithNoMatch()
//    {
//        IfNode ifNode = new IfNode(null, 1);
//        ConditionNode mockCondition = mock(ConditionNode.class);
//        ifNode.addCondition(mockCondition);
//        when(mockCondition.getConditionId()).thenReturn(0);
//
//        assertNull(ifNode.getConditionNode(1));
//    }
//
//    @Test
//    public void testClone()
//    {
//        Tree mockTree = mock(Tree.class);
//        IfNode ifNode = new IfNode(1, mockTree, false);
//        IfNode childIfNodeThen = mock(IfNode.class);
//        IfNode childIfNodeElse = mock(IfNode.class);
//
//        ConditionNode mockCondition = mock(ConditionNode.class);
//        ifNode.addCondition(mockCondition);
//        ifNode.addConditionOperator(BinaryExpr.Operator.OR);
//
//        ifNode.addThenChild(childIfNodeThen);
//        ifNode.addElseChild(childIfNodeElse);
//
//        when(childIfNodeThen.clone()).thenReturn(childIfNodeThen);
//        when(childIfNodeElse.clone()).thenReturn(childIfNodeElse);
//        when(mockCondition.clone()).thenReturn(mockCondition);
//
//        IfNode clone = ifNode.clone();
//        clone.setParentNode(mockTree);
//
//        assertEquals(ifNode, clone);
//        assertNotSame(ifNode, clone);
//    }
//
//    @Test
//    public void testGetTotalConditionCoverageWithNullCoverage()
//    {
//        IfNode ifNode = new IfNode(1, null, false);
//        ConditionNode mockCondition = mock(ConditionNode.class);
//        ifNode.addCondition(mockCondition);
//
//        when(mockCondition.getConditionCoverage()).thenReturn(null);
//
//        assertNull(ifNode.getTotalConditionCoverage());
//    }
//
//    @Test
//    public void testGetTotalConditionCoverageWithOneCondition()
//    {
//        IfNode ifNode = new IfNode(1, null, false);
//        ConditionNode mockCondition = mock(ConditionNode.class);
//        ConditionCoverage mockCoverage = mock(ConditionCoverage.class);
//        ifNode.addCondition(mockCondition);
//
//        when(mockCondition.getConditionCoverage()).thenReturn(mockCoverage);
//
//        assertEquals(mockCoverage, ifNode.getTotalConditionCoverage());
//    }
//
//    @Test
//    public void testGetTotalConditionCoverageWithMultipleConditions()
//    {
//        IfNode ifNode = new IfNode(1, null, false);
//        ConditionNode mockCondition = mock(ConditionNode.class);
//        ifNode.addCondition(mockCondition);
//        ifNode.addConditionOperator(BinaryExpr.Operator.AND);
//        ifNode.addCondition(mockCondition);
//        ifNode.addConditionOperator(BinaryExpr.Operator.OR);
//        ifNode.addCondition(mockCondition);
//        ConditionCoverage coverage1 = new ConditionCoverage(0,true, 2d, 1d);
//        ConditionCoverage coverage2 = new ConditionCoverage(1,true, 1d, 2d);
//        ConditionCoverage coverage3 = new ConditionCoverage(2,false, 5d, 5d);
//        ConditionCoverage expected = new ConditionCoverage(0,null, 6d, 6d);
//
//        when(mockCondition.getConditionCoverage()).thenReturn(coverage1, coverage2, coverage3);
//
//        assertEquals(expected, ifNode.getTotalConditionCoverage());
//    }
//
//    @Test(expectedExceptions = UnsupportedOperationException.class)
//    public void testGetTotalConditionCoverageThrowsUnsupportedException()
//    {
//        IfNode ifNode = new IfNode(1, null, false);
//        ConditionNode mockCondition = mock(ConditionNode.class);
//        ifNode.addCondition(mockCondition);
//        ifNode.addConditionOperator(BinaryExpr.Operator.DIVIDE);
//        ifNode.addCondition(mockCondition);
//        ConditionCoverage coverage1 = new ConditionCoverage(0,true, 2d, 1d);
//        ConditionCoverage coverage2 = new ConditionCoverage(1,true, 1d, 2d);
//
//        when(mockCondition.getConditionCoverage()).thenReturn(coverage1, coverage2);
//
//        ifNode.getTotalConditionCoverage();
//    }
//
//    @Test
//    public void testGetIfNode()
//    {
//        IfNode ifNode = new IfNode(null, 1);
//        IfNode childNode = new IfNode(null, 2);
//        ifNode.addThenChild(childNode);
//
//        assertEquals(ifNode,ifNode.getIfNode(1));
//        assertEquals(childNode,ifNode.getIfNode(2));
//    }
//
//    @Test
//    public void testJoin()
//    {
//        Tree mockParent = mock(Tree.class);
//        IfNode ifNode = new IfNode(mockParent, 1);
//        IfNode childNodeThen = mock(IfNode.class);
//        IfNode childNodeElse = mock(IfNode.class);
//        ConditionNode mockCondition = mock(ConditionNode.class);
//
//        ifNode.addThenChild(childNodeThen);
//        ifNode.addElseChild(childNodeElse);
//        ifNode.addCondition(mockCondition);
//        ifNode.addConditionOperator(BinaryExpr.Operator.OR);
//
//        when(mockParent.getIfNode(anyInt())).thenReturn(ifNode, childNodeThen, childNodeElse);
//        when(mockCondition.join(any())).thenReturn(mockCondition);
//        when(childNodeThen.join(any())).thenReturn(childNodeThen);
//        when(childNodeElse.join(any())).thenReturn(childNodeElse);
//
//        IfNode join = ifNode.join(mockParent);
//        join.setParentNode(mockParent);
//        assertEquals(ifNode, join);
//        assertNotSame(ifNode, join);
//    }
//
//    @Test
//    public void testJoinWithNullOther()
//    {
//        Tree mockTree = mock(Tree.class);
//        IfNode ifNode = new IfNode(null, 1);
//        assertEquals(ifNode, ifNode.join(null));
//
//        IfNode join = ifNode.join(mockTree);
//        assertEquals(ifNode, join);
//        assertNotSame(ifNode, join);
//    }
//}