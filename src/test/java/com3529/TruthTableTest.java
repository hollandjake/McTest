package com3529;

import java.util.Arrays;
import java.util.HashSet;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class TruthTableTest
{
    private Expression a;
    private Expression b;
    private Expression c;
    private Expression equalityComparison;
    private Expression condition;

    @Before
    public void beforeEach()
    {
        a = new NameExpr("a");
        b = new NameExpr("b");
        c = new NameExpr("c");
        equalityComparison = new BinaryExpr(a, new IntegerLiteralExpr("7"), Operator.EQUALS);
        condition = new EnclosedExpr(
                new BinaryExpr(
                        new BinaryExpr(
                                new BinaryExpr(
                                        a,
                                        new BinaryExpr(
                                                new BinaryExpr(
                                                        b,
                                                        a,
                                                        Operator.AND
                                                ),
                                                c,
                                                Operator.AND
                                        ),
                                        Operator.OR
                                ),
                                a,
                                Operator.OR
                        ),
                        equalityComparison,
                        Operator.OR
                )
        );
    }

    @Test
    public void shouldExtractConditions()
    {
        assertEquals(
                Arrays.asList(a, b, a, c, a, equalityComparison),
                TruthTable.extractConditions(condition)
        );
    }

    @Test
    public void shouldExtractVariables()
    {
        assertEquals(
                new HashSet<>(Arrays.asList(a, b, c)),
                TruthTable.extractVariables(condition)
        );
    }

    @Test
    public void shouldGenerateConditionPredicate()
    {
        assertEquals(
                new ConditionPredicate(Arrays.asList(false, false, false, false, false, false), false),
                TruthTable.generateConditionPredicate(
                        condition,
                        Arrays.asList(a, b, a, c, a, equalityComparison),
                        new char[] { ' ', ' ', ' ', ' ', ' ', '0' }
                )
        );
    }

    @Test
    public void shouldGenerateTruthTable()
    {
        TruthTable actual = TruthTable.from(condition);
        assertEquals(condition, actual.getOriginalExpression());
        assertEquals(new HashSet<>(Arrays.asList(a, b, c)), actual.getVariables());
        assertEquals(Arrays.asList(a, b, a, c, a, equalityComparison), actual.getConditionalExpressions());
        assertEquals(64, actual.getConditionPredicates().size());
    }

    @Test
    public void shouldReduceWithMCDC()
    {
        TruthTable truthTable = TruthTable.from(condition);
        TruthTable mcdc = truthTable.toMCDC();

        assertEquals(truthTable.getOriginalExpression(), mcdc.getOriginalExpression());
        assertEquals(truthTable.getVariables(), mcdc.getVariables());
        assertEquals(truthTable.getConditionalExpressions(), mcdc.getConditionalExpressions());
        assertEquals(7, mcdc.getConditionPredicates().size());
    }
}