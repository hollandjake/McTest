package com.github.hollandjake.com3529.generation;

import java.util.Objects;

import com.github.hollandjake.com3529.generation.solver.fitness.FitnessMetric;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.typesafe.config.ConfigFactory;

import lombok.Data;

@Data
public class BranchCoverage implements Cloneable
{
    public static final int K = ConfigFactory.load().getInt("BranchCoverage.K");
    private final int branchNum;
    private final Boolean result;
    private final Double truthDistance;
    private final Double falseDistance;

    public static BranchCoverage from(int conditionId, Expression expr)
    {
        if (expr instanceof BinaryExpr)
        {
            return from(conditionId, (BinaryExpr) expr);
        }
        throw new UnsupportedOperationException(String.format(
                "Expression of type \"%s\" not supported",
                expr.getClass()
        ));
    }

    private static BranchCoverage from(int conditionId, BinaryExpr expr)
    {
        BinaryExpr.Operator operator = expr.getOperator();
        if (operator == BinaryExpr.Operator.AND)
        {
            BranchCoverage left = from(conditionId, expr.getLeft());
            BranchCoverage right = from(conditionId, expr.getRight());
            boolean result = left.result && right.result;
            double falseDistance = Math.min(left.falseDistance, right.falseDistance);
            double truthDistance = Math.max(left.truthDistance, right.truthDistance);
            return new BranchCoverage(conditionId, result, truthDistance, falseDistance);
        }
        else if (operator == BinaryExpr.Operator.OR)
        {
            BranchCoverage left = from(conditionId, expr.getLeft());
            BranchCoverage right = from(conditionId, expr.getRight());
            boolean result = left.result || right.result;
            double falseDistance = left.falseDistance + right.falseDistance;
            double truthDistance = left.truthDistance + right.truthDistance;
            return new BranchCoverage(conditionId, result, truthDistance, falseDistance);
        }

        Double leftNum = expr.getLeft().toDoubleLiteralExpr()
                             .map(DoubleLiteralExpr::asDouble)
                             .orElseGet(() -> evaluate(expr.getLeft()));
        Double rightNum = expr.getRight().toDoubleLiteralExpr()
                              .map(DoubleLiteralExpr::asDouble)
                              .orElseGet(() -> evaluate(expr.getRight()));
        FitnessMetric metric = FitnessMetric.getMetricFor(leftNum, rightNum);


        switch (operator)
        {
            case EQUALS:
                return metric.equals(conditionId, leftNum, rightNum);
            case NOT_EQUALS:
                return metric.notEquals(conditionId, leftNum, rightNum);
            case LESS:
                return metric.less(conditionId, leftNum, rightNum);
            case LESS_EQUALS:
                return metric.lessEquals(conditionId, leftNum, rightNum);
            case GREATER:
                return metric.greater(conditionId, leftNum, rightNum);
            case GREATER_EQUALS:
                return metric.greaterEquals(conditionId, leftNum, rightNum);
            default:
                throw new UnsupportedOperationException(String.format(
                        "Operation \"%s\" not supported!",
                        expr.getOperator()
                ));
        }
    }

    private static double evaluate(Expression expression)
    {
        String expressionString = expression.toString();
        //Fix until https://github.com/mariuszgromada/MathParser.org-mXparser/issues/202 is resolved
        expressionString = expressionString.replaceAll("\\+ ?-|- ?\\+", "-") //+- and -+ handling
                                           .replaceAll("- ?-|\\+ ?\\+", "+"); //-- and ++ handling
        return new org.mariuszgromada.math.mxparser.Expression(expressionString).calculate();
    }

    public static BranchCoverage join(BranchCoverage left, BranchCoverage right)
    {
        if (left != null)
        {
            return left.join(right);
        }
        else if (right != null)
        {
            return right.clone();
        }
        else
        {
            return null;
        }
    }

    @Override
    public BranchCoverage clone()
    {
        return new BranchCoverage(this.branchNum, this.result, this.truthDistance, this.falseDistance);
    }

    public BranchCoverage join(BranchCoverage other)
    {
        if (other == null)
        {
            return this.clone();
        }

        int tempBranchNum;
        Boolean tempResult;
        double tempTruthDistance;
        double tempFalseDistance;

        if (this.branchNum != other.branchNum)
        {
            throw new UnsupportedOperationException("branchNum is not identical");
        }
        else
        {
            tempBranchNum = this.branchNum;
        }

        if (Objects.equals(this.result, other.result))
        {
            tempResult = this.result;
        }
        else
        {
            tempResult = null;
        }

        tempTruthDistance = Math.min(this.truthDistance, other.truthDistance);
        tempFalseDistance = Math.min(this.falseDistance, other.falseDistance);

        return new BranchCoverage(tempBranchNum, tempResult, tempTruthDistance, tempFalseDistance);
    }
}
