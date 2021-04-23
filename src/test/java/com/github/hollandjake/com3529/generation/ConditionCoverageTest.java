//package com.github.hollandjake.com3529.generation;
//
//import com.github.javaparser.ast.expr.BinaryExpr;
//
//import org.mockito.MockedStatic;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyDouble;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.mockStatic;
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertNotSame;
//import static org.testng.AssertJUnit.assertNull;
//
//public class ConditionCoverageTest
//{
//    private MockedStatic<ConditionCoverage> conditionCoverage;
//
//    @BeforeMethod
//    public void setUp()
//    {
//        conditionCoverage = mockStatic(ConditionCoverage.class);
//        conditionCoverage.when(ConditionCoverage::K).thenReturn(1);
//        conditionCoverage.when(() -> ConditionCoverage.from(anyInt(), anyInt(), anyInt(), any())).thenCallRealMethod();
//        conditionCoverage.when(() -> ConditionCoverage.join(any(), any())).thenCallRealMethod();
//        conditionCoverage.when(() -> ConditionCoverage.normalise(anyDouble())).thenCallRealMethod();
//    }
//
//    @AfterMethod
//    public void tearDown()
//    {
//        conditionCoverage.close();
//    }
//
//    @Test
//    public void testFrom()
//    {
//        assertEquals(new ConditionCoverage(0, true, 0d, 1d),
//                     ConditionCoverage.from(0, 1, 1, BinaryExpr.Operator.EQUALS));
//
//        assertEquals(new ConditionCoverage(0, false, 1d, 0d),
//                     ConditionCoverage.from(0, 1, 1, BinaryExpr.Operator.NOT_EQUALS));
//
//        assertEquals(new ConditionCoverage(0, false, 1d, 0d),
//                     ConditionCoverage.from(0, 1, 1, BinaryExpr.Operator.LESS));
//
//        assertEquals(new ConditionCoverage(0, true, 0d, 1d),
//                     ConditionCoverage.from(0, 1, 1, BinaryExpr.Operator.LESS_EQUALS));
//
//        assertEquals(new ConditionCoverage(0, false, 1d, 0d),
//                     ConditionCoverage.from(0, 1, 1, BinaryExpr.Operator.GREATER));
//
//        assertEquals(new ConditionCoverage(0, true, 0d, 1d),
//                     ConditionCoverage.from(0, 1, 1, BinaryExpr.Operator.GREATER_EQUALS));
//    }
//
//    @Test(expectedExceptions = UnsupportedOperationException.class)
//    public void testFromThrowsUnsupported()
//    {
//        ConditionCoverage.from(0, 1, 1, BinaryExpr.Operator.XOR);
//    }
//
//    @Test
//    public void testJoin()
//    {
//        ConditionCoverage left = new ConditionCoverage(0, true, 0d, 1d);
//        ConditionCoverage right = new ConditionCoverage(0, false, 1d, 0d);
//
//        ConditionCoverage expected = new ConditionCoverage(0, null, 0d, 0d);
//        assertEquals(expected, ConditionCoverage.join(left, right));
//    }
//
//    @Test
//    public void testJoinLeftNull()
//    {
//        ConditionCoverage left = null;
//        ConditionCoverage right = new ConditionCoverage(0, false, 1d, 0d);
//
//        ConditionCoverage result = ConditionCoverage.join(left, right);
//        assertEquals(result, right);
//        assertNotSame(result, right);
//    }
//
//    @Test
//    public void testJoinRightNull()
//    {
//        ConditionCoverage left = new ConditionCoverage(0, true, 0d, 1d);
//        ConditionCoverage right = null;
//
//        ConditionCoverage result = ConditionCoverage.join(left, right);
//        assertEquals(result, left);
//        assertNotSame(result, left);
//    }
//
//    @Test
//    public void testJoinBothNull()
//    {
//        assertNull(ConditionCoverage.join(null, null));
//    }
//
//    @Test
//    public void testClone()
//    {
//        ConditionCoverage conditionCoverage = new ConditionCoverage(0, true, 0d, 1d);
//        ConditionCoverage clone = conditionCoverage.clone();
//        assertEquals(clone, conditionCoverage);
//        assertNotSame(clone, conditionCoverage);
//    }
//
//    @Test(expectedExceptions = UnsupportedOperationException.class)
//    public void testJoinConditionIdMismatch()
//    {
//        ConditionCoverage.join(
//                new ConditionCoverage(0, true, 0d, 0d),
//                new ConditionCoverage(1, true, 0d, 0d)
//        );
//    }
//
//    @Test
//    public void testJoinMatchingResult()
//    {
//        ConditionCoverage left = new ConditionCoverage(0, true, 1d, 1d);
//        ConditionCoverage right = new ConditionCoverage(0, true, 1d, 1d);
//        ConditionCoverage expected = new ConditionCoverage(0, true, 1d, 1d);
//        assertEquals(ConditionCoverage.join(left, right), expected);
//    }
//
//    @Test
//    public void testGetNormalisedFitness()
//    {
//        ConditionCoverage conditionCoverage = new ConditionCoverage(0 ,true, 1d, 1d);
//
//        assertEquals(conditionCoverage.getNormalisedFitness(), 2/3d);
//    }
//}