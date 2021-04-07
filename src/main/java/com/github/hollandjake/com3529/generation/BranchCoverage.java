package com.github.hollandjake.com3529.generation;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.google.common.util.concurrent.AtomicDouble;
import com.typesafe.config.ConfigFactory;

import lombok.Data;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class BranchCoverage implements Cloneable
{
    private static final int K = ConfigFactory.load().getInt("BranchCoverage.K");
    private final int branchNum;
    private final Boolean result;
    private final Double truthDistance;
    private final Double falseDistance;

    public static BranchCoverage from(int branchNum, Expression expr)
    {
        if (expr instanceof BinaryExpr)
        {
            return from(branchNum, (BinaryExpr) expr);
        }
        throw new UnsupportedOperationException(String.format(
                "Expression of type \"%s\" not supported",
                expr.getClass()
        ));
    }

    private static BranchCoverage from(int branchNum, BinaryExpr expr)
    {
        Double leftNum = expr.getLeft().toDoubleLiteralExpr()
                             .map(DoubleLiteralExpr::asDouble)
                             .orElseGet(() -> evaluate(expr.getLeft()));
        Double rightNum = expr.getRight().toDoubleLiteralExpr()
                             .map(DoubleLiteralExpr::asDouble)
                             .orElseGet(() -> evaluate(expr.getRight()));

        boolean result;
        double truthDistance;
        double falseDistance;

        switch (expr.getOperator())
        {
            case EQUALS:
                result = leftNum.equals(rightNum);
                truthDistance = Math.abs(leftNum - rightNum);
                falseDistance = truthDistance == 0 ? K : 0;
                break;
            case NOT_EQUALS:
                result = !leftNum.equals(rightNum);
                falseDistance = Math.abs(leftNum - rightNum);
                truthDistance = falseDistance == 0 ? K : 0;
                break;
            case LESS:
                result = leftNum < rightNum;
                falseDistance = result ? rightNum - leftNum + K : 0;
                truthDistance = result ? 0 : leftNum - rightNum + K;
                break;
            case LESS_EQUALS:
                result = leftNum <= rightNum;
                falseDistance = result ? rightNum - leftNum + K : 0;
                truthDistance = result ? 0 : leftNum - rightNum + K;
                break;
            case GREATER:
                result = leftNum > rightNum;
                falseDistance = result ? leftNum - rightNum + K : 0;
                truthDistance = result ? 0 : rightNum - leftNum + K;
                break;
            case GREATER_EQUALS:
                result = leftNum >= rightNum;
                falseDistance = result ? leftNum - rightNum + K : 0;
                truthDistance = result ? 0 : rightNum - leftNum + K;
                break;
            default:
                throw new UnsupportedOperationException(String.format(
                        "Operation \"%s\" not supported!",
                        expr.getOperator()
                ));
        }

        return new BranchCoverage(branchNum, result, truthDistance, falseDistance);
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
