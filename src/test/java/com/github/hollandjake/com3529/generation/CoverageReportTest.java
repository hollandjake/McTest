package com.github.hollandjake.com3529.generation;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.hollandjake.com3529.utils.tree.ConditionNode;
import com.github.hollandjake.com3529.utils.tree.IfNode;
import com.github.hollandjake.com3529.utils.tree.Tree;
import com.github.javaparser.ast.expr.BinaryExpr;

import org.mockito.MockedStatic;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class CoverageReportTest
{
    private MockedStatic<ConditionCoverage> conditionCoverage;

    private Tree treeMock;
    private CoverageReport report;

    @BeforeMethod
    public void setUp()
    {
        conditionCoverage = mockStatic(ConditionCoverage.class);
        conditionCoverage.when(ConditionCoverage::K).thenReturn(1);
        conditionCoverage.when(() -> ConditionCoverage.from(anyInt(), anyInt(), anyInt(), any()))
                         .thenReturn(new ConditionCoverage(0, true, 0d, 0d));
        conditionCoverage.when(() -> ConditionCoverage.join(any(), any())).thenCallRealMethod();
        conditionCoverage.when(() -> ConditionCoverage.normalise(anyDouble())).thenCallRealMethod();

        treeMock = mock(Tree.class);
        doCallRealMethod().when(treeMock).forEach(any());
        when(treeMock.iterator()).thenCallRealMethod();
        when(treeMock.clone()).thenReturn(treeMock);
        report = new CoverageReport(treeMock);
        verify(treeMock, times(1)).clone();
    }

    @AfterMethod
    public void tearDown()
    {
        conditionCoverage.close();
    }

    @Test
    public void testCover()
    {
        verify(treeMock, never()).getConditionNode(anyInt());
        ConditionNode mockNode = mock(ConditionNode.class);
        when(treeMock.getConditionNode(anyInt())).thenReturn(mockNode);
        assertTrue(report.cover(0, 1, 1, BinaryExpr.Operator.EQUALS));
        verify(mockNode, times(1)).setConditionCoverage(any());
        verify(treeMock, times(1)).getConditionNode(anyInt());
    }

    @Test
    public void testGetFitness()
    {
        IfNode mockNode1 = mock(IfNode.class);
        IfNode mockNode2 = mock(IfNode.class);

        when(treeMock.getAllChildren()).thenReturn(Arrays.asList(mockNode1, mockNode2));
        when(mockNode1.getRawFitness()).thenReturn(1d);
        when(mockNode2.getRawFitness()).thenReturn(2d);

        assertEquals(3d, report.getFitness());
    }

    @Test
    public void testJoin()
    {
        when(treeMock.join(treeMock)).thenReturn(treeMock);
        assertEquals(report, report.join(report));
    }

    @Test
    public void testGetBranchesCovered()
    {
        IfNode mockNode1 = mock(IfNode.class);
        IfNode mockNode2 = mock(IfNode.class);

        ConditionCoverage coverage1 = new ConditionCoverage(0, true, 0d, 1d);
        ConditionCoverage coverage2 = new ConditionCoverage(0, false, 1d, 0d);
        ConditionCoverage coverage3 = new ConditionCoverage(1, null, 0d, 0d);

        ConditionNode mockConditionNode1 = mock(ConditionNode.class);
        ConditionNode mockConditionNode2 = mock(ConditionNode.class);
        ConditionNode mockConditionNode3 = mock(ConditionNode.class);
        ConditionNode mockConditionNode4 = mock(ConditionNode.class);

        when(treeMock.getAllChildren()).thenReturn(Arrays.asList(mockNode1, mockNode2));
        when(mockNode1.getConditions()).thenReturn(Arrays.asList(mockConditionNode1, mockConditionNode2));
        when(mockNode2.getConditions()).thenReturn(Arrays.asList(mockConditionNode3, mockConditionNode4));

        when(mockConditionNode1.getConditionCoverage()).thenReturn(coverage1);
        when(mockConditionNode1.getConditionId()).thenReturn(0);
        when(mockConditionNode2.getConditionCoverage()).thenReturn(coverage2);
        when(mockConditionNode2.getConditionId()).thenReturn(0);
        when(mockConditionNode3.getConditionCoverage()).thenReturn(coverage3);
        when(mockConditionNode3.getConditionId()).thenReturn(1);
        when(mockConditionNode4.getConditionCoverage()).thenReturn(null);

        Set<String> result = report.getBranchesCovered();
        assertEquals(new HashSet<>(Arrays.asList("0t", "0f", "1t", "1f")), result);
    }

    @Test
    public void testGetConditionNodes()
    {
        IfNode mockNode = mock(IfNode.class);
        ConditionNode mockConditionNode = mock(ConditionNode.class);

        when(treeMock.getAllChildren()).thenReturn(Arrays.asList(mockNode, mockNode));

        when(mockNode.getConditions()).thenReturn(Collections.singletonList(mockConditionNode));

        List<ConditionNode> result = report.getConditionNodes();

        assertEquals(result, Arrays.asList(mockConditionNode, mockConditionNode));
    }
}