package com.github.hollandjake.com3529.utils.tree;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class TreeTest
{
    @Test
    public void testAddChild()
    {
        Tree tree = new Tree();
        BranchNode childNode = new BranchNode(null, 1);
        tree.addChild(childNode);

        assertThat(tree.getChildren(), hasItem(childNode));
        assertEquals(childNode.getParentNode(), tree);
    }

    @Test
    public void testGetConditionNode()
    {
        BranchNode childNode = mock(BranchNode.class);
        Tree tree = new Tree(Arrays.asList(childNode, childNode, childNode));

        ConditionNode conditionNode = mock(ConditionNode.class);

        when(childNode.getConditionNode(anyInt())).thenReturn(null, null, conditionNode, null, null, null);

        assertEquals(conditionNode, tree.getConditionNode(1));
        assertNull(tree.getConditionNode(1));
    }

    @Test
    public void testGetIfNode()
    {
        BranchNode childNode = mock(BranchNode.class);
        Tree tree = new Tree(Arrays.asList(childNode, childNode, childNode));

        when(childNode.getBranchNode(anyInt())).thenReturn(null, null, childNode, null, null, null);
        assertEquals(childNode, tree.getBranchNode(1));
        assertNull(tree.getBranchNode(1));
    }

    @Test
    public void testGetAllChildren()
    {
        BranchNode childNode = new BranchNode(null, 1);
        BranchNode nestedChildNode = new BranchNode(null, 2);
        childNode.addThenChild(nestedChildNode);

        Tree tree = new Tree(Collections.singletonList(childNode));

        assertThat(tree.getAllChildren(), containsInAnyOrder(childNode, nestedChildNode));
    }

    @Test
    public void testClone()
    {
        BranchNode childNode = mock(BranchNode.class);
        Tree tree = new Tree(Collections.singletonList(childNode));

        when(childNode.clone()).thenReturn(childNode);

        Tree clone = tree.clone();
        assertEquals(clone, tree);
        assertNotSame(clone, tree);
    }

    @Test
    public void testIterator()
    {
        BranchNode childNode = mock(BranchNode.class);
        Tree tree = new Tree(Arrays.asList(childNode, childNode, childNode));

        Iterator<BranchNode> iterable = tree.iterator();

        assertTrue(iterable.hasNext());
        assertEquals(iterable.next(), childNode);
        assertEquals(iterable.next(), childNode);
        assertEquals(iterable.next(), childNode);
        assertFalse(iterable.hasNext());
    }

    @Test
    public void testGetFitness()
    {
        Tree tree = new Tree();
        assertEquals(0d, tree.getFitness());
        assertEquals(0d, tree.getFitness(true));
        assertEquals(0d, tree.getFitness(false));
    }

    @Test
    public void testJoin()
    {
        BranchNode childNode = mock(BranchNode.class);
        Tree tree1 = new Tree(Collections.singletonList(childNode));
        Tree tree2 = new Tree(Collections.singletonList(childNode));

        when(childNode.join(any())).thenReturn(childNode);

        assertEquals(tree1, tree1.join(tree2));
        assertEquals(tree1, tree1.join(null));
    }
}