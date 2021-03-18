package com3529;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.Type;

import junit.framework.TestCase;

import org.junit.Test;

import javax.script.ScriptException;

import java.util.Map;

public class TruthTableTest
{
    @Test
    public void AlessthanB()
    {
        // a or (b and c)
        Expression a = new NameExpr("a");
        Expression b = new NameExpr("b");

        BinaryExpr.Operator lessThan = BinaryExpr.Operator.LESS;
        BinaryExpr.Operator and = BinaryExpr.Operator.AND;
        BinaryExpr.Operator or = BinaryExpr.Operator.OR;

        BinaryExpr expressionA = new BinaryExpr(a, b, lessThan);
        BinaryExpr expressionB = new BinaryExpr(a, b, lessThan);
        BinaryExpr expressionC = new BinaryExpr(a, b, lessThan);

        BinaryExpr andExpression = new BinaryExpr(expressionB, expressionC, and);
        BinaryExpr finalExpression = new BinaryExpr(expressionA, andExpression, or);
        BinaryExpr finalExpression1 = new BinaryExpr(finalExpression, expressionC, and);
        BinaryExpr finalExpression2 = new BinaryExpr(finalExpression1, expressionA, or);

        TruthTable truthTable = TruthTable.from(finalExpression).toMCDC();
    }
}