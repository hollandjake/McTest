package com3529;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap;

import lombok.Data;

@Data
public class TruthTable
{
    private static final Pattern GET_OPERATORS = Pattern.compile("(\\|\\|)|(&&)");

    private final List<ConditionPredicate> conditionPredicates;

    private final int numberOfConditions;

    private static final List<BinaryExpr.Operator> LOGICAL_OPERATORS = Arrays.asList(
            BinaryExpr.Operator.OR,
            BinaryExpr.Operator.AND,
            BinaryExpr.Operator.BINARY_OR,
            BinaryExpr.Operator.BINARY_AND,
            BinaryExpr.Operator.XOR
    );

    private static Expression convertExpressionToBooleanComponents(Expression expression)
    {
        Expression expressionToProcess;
        UnaryOperator<Expression> placeholderSupplier;

        if (expression instanceof EnclosedExpr)
        {
            expressionToProcess = ((EnclosedExpr) expression).getInner();
            placeholderSupplier = expr -> expr;
        }
        else if (expression instanceof UnaryExpr)
        {
            expressionToProcess = ((UnaryExpr) expression).getExpression();
            placeholderSupplier = expr -> new UnaryExpr(expr, ((UnaryExpr) expression).getOperator());
        }
        else
        {
            expressionToProcess = expression;
            placeholderSupplier = expr -> expr;
        }

        if (expressionToProcess instanceof BinaryExpr &&
                LOGICAL_OPERATORS.contains(((BinaryExpr) expressionToProcess).getOperator()))
        {
            BinaryExpr binaryExpressionToProcess = (BinaryExpr) expressionToProcess;
            Expression left = convertExpressionToBooleanComponents(binaryExpressionToProcess.getLeft());
            Expression right = convertExpressionToBooleanComponents(binaryExpressionToProcess.getRight());

            return placeholderSupplier.apply(new BinaryExpr(left, right, binaryExpressionToProcess.getOperator()));
        }
        else
        {
            return placeholderSupplier.apply(new PlaceholderExpr());
        }
    }

    public static TruthTable from(Expression expression)
    {
        //Extract all conditions as placeholders
        Expression booleanExpression = convertExpressionToBooleanComponents(expression);

        //Count number of placeholders
        List<PlaceholderExpr> placeholderExprs = new ArrayList<>();
        booleanExpression.accept(new VoidVisitorAdapter<Void>()
        {
            public void visit(NameExpr n, Void arg) {
                if (n instanceof PlaceholderExpr)
                {
                    placeholderExprs.add((PlaceholderExpr) n);
                }
                super.visit(n, arg);
            }
        }, null);

        int n = placeholderExprs.size();

        List<ConditionPredicate> conditionPredicates = new ArrayList<>();

        String binaryFormatPattern = "%" + n + "s";

        //Generate all permutations of conditions and evaluate the expressions
        for (int i = 0; i < 1 << n; i++)
        {
            List<Boolean> conditions = new ArrayList<>();
            char[] binaryCharSequence = String.format(binaryFormatPattern, Integer.toBinaryString(i)).toCharArray();

            AtomicInteger j = new AtomicInteger(0);
            Expression appliedExpression = (Expression) booleanExpression.accept(new CloneVisitorWithPlaceholder(), null);
            Expression newAppliedExpression = (Expression) appliedExpression.accept(new ModifierVisitor<Void>()
            {
                @Override
                public Visitable visit(NameExpr n, Void arg) {
                    if (n instanceof PlaceholderExpr)
                    {
                        boolean isTruthy = binaryCharSequence[j.getAndIncrement()] == '1';
                        conditions.add(isTruthy);
                        return new IntegerLiteralExpr(isTruthy ? "1" : "0");
                    }
                    return super.visit(n, arg);
                }
            }, null);

            boolean predicate = new org.mariuszgromada.math.mxparser.Expression(appliedExpression.toString()).calculate() == 1;

            conditionPredicates.add(new ConditionPredicate(conditions, predicate));
        }

        return new TruthTable(conditionPredicates, n);
    }

    public TruthTable toMCDC()
    {
        Set<ConditionPredicate> newConditionPredicates = new HashSet<>();

        for (int i = 0; i < numberOfConditions; i++)
        {
            ConditionPredicate truthy = null;
            ConditionPredicate falsey = null;
            for (ConditionPredicate conditionPredicate : this.conditionPredicates)
            {
                List<Boolean> conditions = conditionPredicate.getConditions();
                if (conditions.get(i))
                {
                    if (truthy == null)
                    {
                        if (conditionPredicate.getPredicate())
                        {
                            truthy = conditionPredicate;
                        }
                    }
                }
                else
                {
                    if (falsey == null)
                    {
                        if (!conditionPredicate.getPredicate())
                        {
                            falsey = conditionPredicate;
                        }
                    }
                }
            }
            newConditionPredicates.add(truthy);
            newConditionPredicates.add(falsey);
        }

        List<ConditionPredicate> newListConditionPredicates = new ArrayList<>(newConditionPredicates);

        return new TruthTable(newListConditionPredicates, this.numberOfConditions);
    }
}
