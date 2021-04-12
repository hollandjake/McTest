//package com.github.hollandjake.com3529.generation;
//
//import com.github.javaparser.StaticJavaParser;
//import com.github.javaparser.ast.expr.AssignExpr;
//import com.github.javaparser.ast.expr.BinaryExpr;
//import com.github.javaparser.ast.expr.BooleanLiteralExpr;
//import com.github.javaparser.ast.expr.Expression;
//
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
//
//public class BranchCoverageTest
//{
//    @Test(expected = UnsupportedOperationException.class)
//    public void testFromThrowsErrorWhenExpressionTypeIsNotSupported()
//    {
//        BranchCoverage.from(0, new AssignExpr());
//    }
//
//    @Test(expected = UnsupportedOperationException.class)
//    public void testFromThrowsErrorWhenExpressionOperatorIsNotSupported()
//    {
//        BranchCoverage.from(0, StaticJavaParser.parseExpression(String.format("%s ^ %s", true, true)));
//    }
//
//    @Test
//    public void testFromEqualsTrue()
//    {
//        double left = 5;
//        double right = 5;
//        Expression expression = StaticJavaParser.parseExpression(String.format("%s == %s", left, right));
//        BranchCoverage expected = new BranchCoverage(0, true, 0d, 1d);
//        assertEquals(expected, BranchCoverage.from(0, expression));
//    }
//
//    @Test
//    public void testFromEqualsFalse()
//    {
//        double left = 6;
//        double right = 5;
//        Expression expression = StaticJavaParser.parseExpression(String.format("%s == %s", left, right));
//        BranchCoverage expected = new BranchCoverage(0, false, 1d, 0d);
//        assertEquals(expected, BranchCoverage.from(0, expression));
//    }
//
//    @Test
//    public void testFromNotEqualsTrue()
//    {
//        double left = 6;
//        double right = 5;
//        Expression expression = StaticJavaParser.parseExpression(String.format("%s != %s", left, right));
//        BranchCoverage expected = new BranchCoverage(0, true, 0d, 1d);
//        assertEquals(expected, BranchCoverage.from(0, expression));
//    }
//
//    @Test
//    public void testFromNotEqualsFalse()
//    {
//        double left = 5;
//        double right = 5;
//        Expression expression = StaticJavaParser.parseExpression(String.format("%s != %s", left, right));
//        BranchCoverage expected = new BranchCoverage(0, false, 1d, 0d);
//        assertEquals(expected, BranchCoverage.from(0, expression));
//    }
//
//    @Test
//    public void testFromLessTrue()
//    {
//        double left = 2;
//        double right = 5;
//        Expression expression = StaticJavaParser.parseExpression(String.format("%s < %s", left, right));
//        BranchCoverage expected = new BranchCoverage(0, true, 0d, 4d);
//        assertEquals(expected, BranchCoverage.from(0, expression));
//    }
//
//    @Test
//    public void testFromLessFalse()
//    {
//        double left = 5;
//        double right = 5;
//        Expression expression = StaticJavaParser.parseExpression(String.format("%s < %s", left, right));
//        BranchCoverage expected = new BranchCoverage(0, false, 1d, 0d);
//        assertEquals(expected, BranchCoverage.from(0, expression));
//    }
//
//    @Test
//    public void testFromLessEqualsTrue()
//    {
//        double left = 2;
//        double right = 5;
//        Expression expression = StaticJavaParser.parseExpression(String.format("%s <= %s", left, right));
//        BranchCoverage expected = new BranchCoverage(0, true, 0d, 4d);
//        assertEquals(expected, BranchCoverage.from(0, expression));
//
//        left = 5;
//        expression = StaticJavaParser.parseExpression(String.format("%s <= %s", left, right));
//        expected = new BranchCoverage(0, true, 0d, 1d);
//        assertEquals(expected, BranchCoverage.from(0, expression));
//    }
//
//    @Test
//    public void testFromLessEqualsFalse()
//    {
//        double left = 6;
//        double right = 5;
//        Expression expression = StaticJavaParser.parseExpression(String.format("%s <= %s", left, right));
//        BranchCoverage expected = new BranchCoverage(0, false, 2d, 0d);
//        assertEquals(expected, BranchCoverage.from(0, expression));
//    }
//
//    @Test
//    public void testFromGreaterTrue()
//    {
//        double left = 8;
//        double right = 5;
//        Expression expression = StaticJavaParser.parseExpression(String.format("%s > %s", left, right));
//        BranchCoverage expected = new BranchCoverage(0, true, 0d, 4d);
//        assertEquals(expected, BranchCoverage.from(0, expression));
//    }
//
//    @Test
//    public void testFromGreaterFalse()
//    {
//        double left = 5;
//        double right = 5;
//        Expression expression = StaticJavaParser.parseExpression(String.format("%s > %s", left, right));
//        BranchCoverage expected = new BranchCoverage(0, false, 1d, 0d);
//        assertEquals(expected, BranchCoverage.from(0, expression));
//    }
//
//    @Test
//    public void testFromGreaterEqualsTrue()
//    {
//        double left = 5;
//        double right = 2;
//        Expression expression = StaticJavaParser.parseExpression(String.format("%s >= %s", left, right));
//        BranchCoverage expected = new BranchCoverage(0, true, 0d, 4d);
//        assertEquals(expected, BranchCoverage.from(0, expression));
//
//        right = 5;
//        expression = StaticJavaParser.parseExpression(String.format("%s <= %s", left, right));
//        expected = new BranchCoverage(0, true, 0d, 1d);
//        assertEquals(expected, BranchCoverage.from(0, expression));
//    }
//
//    @Test
//    public void testFromGreaterEqualsFalse()
//    {
//        double left = 5;
//        double right = 6;
//        Expression expression = StaticJavaParser.parseExpression(String.format("%s >= %s", left, right));
//        BranchCoverage expected = new BranchCoverage(0, false, 2d, 0d);
//        assertEquals(expected, BranchCoverage.from(0, expression));
//    }
//
//    @Test
//    public void testJoinLeftAndRight()
//    {
//        BranchCoverage left = new BranchCoverage(0, true, 1d,0d);
//        BranchCoverage right = new BranchCoverage(0, false,0d,1d);
//
//        BranchCoverage result = BranchCoverage.join(left, right);
//        BranchCoverage expected = new BranchCoverage(0, null, 0d,0d);
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void testJoinLeftAndRightWithMatchingResult()
//    {
//        BranchCoverage left = new BranchCoverage(0, true, 1d,0d);
//        BranchCoverage right = new BranchCoverage(0, true,0d,1d);
//
//        BranchCoverage result = BranchCoverage.join(left, right);
//        BranchCoverage expected = new BranchCoverage(0, true, 0d,0d);
//
//        assertEquals(expected, result);
//    }
//
//    @Test(expected = UnsupportedOperationException.class)
//    public void testJoinWithMismatchBranchId()
//    {
//        BranchCoverage left = new BranchCoverage(1, true, 1d,0d);
//        BranchCoverage right = new BranchCoverage(0, false,0d,1d);
//
//        BranchCoverage.join(left, right);
//    }
//
//    @Test
//    public void testJoinNullAndRight()
//    {
//        BranchCoverage right = new BranchCoverage(0, false,0d,1d);
//
//        BranchCoverage result = BranchCoverage.join(null, right);
//        BranchCoverage expected = new BranchCoverage(0, false, 0d,1d);
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void testJoinLeftAndNull()
//    {
//        BranchCoverage left = new BranchCoverage(0, true, 1d,0d);
//
//        BranchCoverage result = BranchCoverage.join(left, null);
//        BranchCoverage expected = new BranchCoverage(0, true, 1d,0d);
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void testJoinNullAndNull()
//    {
//        assertNull(BranchCoverage.join(null, null));
//    }
//}
