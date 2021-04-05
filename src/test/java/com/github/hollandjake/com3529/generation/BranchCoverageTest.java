package com.github.hollandjake.com3529.generation;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.Expression;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BranchCoverageTest
{
    @Test
    public void testFromEqualsTrue()
    {
        double left = 5;
        double right = 5;
        Expression expression = StaticJavaParser.parseExpression(String.format("%s == %s", left, right));
        BranchCoverage expected = new BranchCoverage(0, true, 0d, 1d);
        assertEquals(expected, BranchCoverage.from(0, expression));
    }

    @Test
    public void testFromEqualsFalse()
    {
        double left = 6;
        double right = 5;
        Expression expression = StaticJavaParser.parseExpression(String.format("%s == %s", left, right));
        BranchCoverage expected = new BranchCoverage(0, false, 1d, 0d);
        assertEquals(expected, BranchCoverage.from(0, expression));
    }

    @Test
    public void testFromNotEqualsTrue()
    {
        double left = 6;
        double right = 5;
        Expression expression = StaticJavaParser.parseExpression(String.format("%s != %s", left, right));
        BranchCoverage expected = new BranchCoverage(0, true, 0d, 1d);
        assertEquals(expected, BranchCoverage.from(0, expression));
    }

    @Test
    public void testFromNotEqualsFalse()
    {
        double left = 5;
        double right = 5;
        Expression expression = StaticJavaParser.parseExpression(String.format("%s != %s", left, right));
        BranchCoverage expected = new BranchCoverage(0, false, 1d, 0d);
        assertEquals(expected, BranchCoverage.from(0, expression));
    }

    @Test
    public void testFromLessTrue()
    {
        double left = 2;
        double right = 5;
        Expression expression = StaticJavaParser.parseExpression(String.format("%s < %s", left, right));
        BranchCoverage expected = new BranchCoverage(0, true, 0d, 4d);
        assertEquals(expected, BranchCoverage.from(0, expression));
    }

    @Test
    public void testFromLessFalse()
    {
        double left = 5;
        double right = 5;
        Expression expression = StaticJavaParser.parseExpression(String.format("%s < %s", left, right));
        BranchCoverage expected = new BranchCoverage(0, false, 1d, 0d);
        assertEquals(expected, BranchCoverage.from(0, expression));
    }

    @Test
    public void testFromLessEqualsTrue()
    {
        double left = 2;
        double right = 5;
        Expression expression = StaticJavaParser.parseExpression(String.format("%s <= %s", left, right));
        BranchCoverage expected = new BranchCoverage(0, true, 0d, 4d);
        assertEquals(expected, BranchCoverage.from(0, expression));

        left = 5;
        expression = StaticJavaParser.parseExpression(String.format("%s <= %s", left, right));
        expected = new BranchCoverage(0, true, 0d, 1d);
        assertEquals(expected, BranchCoverage.from(0, expression));
    }

    @Test
    public void testFromLessEqualsFalse()
    {
        double left = 6;
        double right = 5;
        Expression expression = StaticJavaParser.parseExpression(String.format("%s <= %s", left, right));
        BranchCoverage expected = new BranchCoverage(0, false, 2d, 0d);
        assertEquals(expected, BranchCoverage.from(0, expression));
    }

    @Test
    public void testFromGreaterTrue()
    {
        double left = 8;
        double right = 5;
        Expression expression = StaticJavaParser.parseExpression(String.format("%s > %s", left, right));
        BranchCoverage expected = new BranchCoverage(0, true, 0d, 4d);
        assertEquals(expected, BranchCoverage.from(0, expression));
    }

    @Test
    public void testFromGreaterFalse()
    {
        double left = 5;
        double right = 5;
        Expression expression = StaticJavaParser.parseExpression(String.format("%s > %s", left, right));
        BranchCoverage expected = new BranchCoverage(0, false, 1d, 0d);
        assertEquals(expected, BranchCoverage.from(0, expression));
    }

    @Test
    public void testFromGreaterEqualsTrue()
    {
        double left = 5;
        double right = 2;
        Expression expression = StaticJavaParser.parseExpression(String.format("%s >= %s", left, right));
        BranchCoverage expected = new BranchCoverage(0, true, 0d, 4d);
        assertEquals(expected, BranchCoverage.from(0, expression));

        right = 5;
        expression = StaticJavaParser.parseExpression(String.format("%s <= %s", left, right));
        expected = new BranchCoverage(0, true, 0d, 1d);
        assertEquals(expected, BranchCoverage.from(0, expression));
    }

    @Test
    public void testFromGreaterEqualsFalse()
    {
        double left = 5;
        double right = 6;
        Expression expression = StaticJavaParser.parseExpression(String.format("%s >= %s", left, right));
        BranchCoverage expected = new BranchCoverage(0, false, 2d, 0d);
        assertEquals(expected, BranchCoverage.from(0, expression));
    }
}
