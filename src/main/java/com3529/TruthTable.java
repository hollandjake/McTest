package com3529;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import lombok.Data;

@Data
public class TruthTable
{
    private final Expression originalExpression;
    private final Set<NameExpr> variables;
    private final List<Expression> conditionalExpressions;
    private final List<ConditionPredicate> conditionPredicates;

    private static final List<BinaryExpr.Operator> LOGICAL_OPERATORS = Arrays.asList(
            BinaryExpr.Operator.OR,
            BinaryExpr.Operator.AND,
            BinaryExpr.Operator.BINARY_OR,
            BinaryExpr.Operator.BINARY_AND,
            BinaryExpr.Operator.XOR
    );

    public static TruthTable from(Expression expression)
    {
        Set<NameExpr> variables = extractVariables(expression);
        List<Expression> conditions = extractConditions(expression);

        List<ConditionPredicate> conditionPredicates = new ArrayList<>();

        int n = conditions.size();
        String binaryFormatPattern = "%" + n + "s";

        for (int i = 0; i < 1 << n; i++)
        {
            char[] binaryCharSequence = String.format(binaryFormatPattern, Integer.toBinaryString(i)).toCharArray();

            conditionPredicates.add(generateConditionPredicate(expression, conditions, binaryCharSequence));
        }

        return new TruthTable(expression, variables, conditions, conditionPredicates);
    }

    public static ConditionPredicate generateConditionPredicate(Expression expression,
            List<Expression> conditions,
            char[] binaryCharSequence)
    {
        List<Boolean> conditionalStates = new ArrayList<>();
        AtomicInteger i = new AtomicInteger(0);
        Expression newAppliedExpression = (Expression) expression.clone().accept(new ModifierVisitorWithExpression()
        {
            @Override
            public Visitable visit(Expression n, Void arg)
            {
                if (conditions.contains(n))
                {
                    boolean isTruthy = binaryCharSequence[i.getAndIncrement()] == '1';
                    conditionalStates.add(isTruthy);
                    return new IntegerLiteralExpr(isTruthy ? "1" : "0");
                }
                return super.visit(n, arg);
            }
        }, null);

        boolean predicate = new org.mariuszgromada.math.mxparser.Expression(newAppliedExpression.toString()).calculate()
                == 1;

        return new ConditionPredicate(conditionalStates, predicate);
    }

    public static List<Expression> extractConditions(Expression expression)
    {
        List<Expression> conditions = new ArrayList<>();

        expression.accept(new VoidVisitorAdapterWithExpression()
        {
            @Override
            public void visit(BinaryExpr n, Void arg)
            {
                if (!LOGICAL_OPERATORS.contains(n.getOperator()))
                {
                    conditions.add(n);
                }
                else
                {
                    n.getLeft().accept(this, arg);
                    n.getRight().accept(this, arg);
                    n.getComment().ifPresent(l -> l.accept(this, arg));
                }
            }

            @Override
            public void visit(EnclosedExpr n, Void arg)
            {
                n.getInner().accept(this, arg);
            }

            @Override
            public void visit(UnaryExpr n, Void arg)
            {
                n.getExpression().accept(this, arg);
            }

            @Override
            public void visit(Expression n, Void arg)
            {
                conditions.add(n);
                super.visit(n, arg);
            }
        }, null);

        return conditions;
    }

    public static Set<NameExpr> extractVariables(Expression expression)
    {
        Set<NameExpr> variables = new HashSet<>();

        expression.accept(new VoidVisitorAdapter<Void>()
        {
            @Override
            public void visit(NameExpr n, Void arg)
            {
                variables.add(n);
            }
        }, null);

        return variables;
    }

    public TruthTable toMCDC()
    {
        int numberOfConditionalExpressions = conditionalExpressions.size();

        Set<ConditionPredicate> newConditionPredicates = new HashSet<>();

        for (int i = 0; i < numberOfConditionalExpressions; i++)
        {
            ConditionPredicate truthy = null;
            ConditionPredicate falsey = null;
            for (ConditionPredicate conditionPredicate : this.conditionPredicates)
            {
                if (conditionPredicate.getConditions().get(i))
                {
                    if (truthy == null && conditionPredicate.getPredicate())
                    {
                        truthy = conditionPredicate;
                    }
                }
                else if (falsey == null && !conditionPredicate.getPredicate())
                {
                    falsey = conditionPredicate;
                }
            }
            newConditionPredicates.add(truthy);
            newConditionPredicates.add(falsey);
        }

        List<ConditionPredicate> newListConditionPredicates = new ArrayList<>(newConditionPredicates);

        return new TruthTable(this.originalExpression,
                              this.variables,
                              this.conditionalExpressions,
                              newListConditionPredicates);
    }
}
