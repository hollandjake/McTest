package com.github.hollandjake.com3529.generation;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;

import lombok.Data;

@Data
public class BranchCoverage
{
    private static final int K = 1;
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
        Double leftNum = evaluate(expr.getLeft());
        Double rightNum = evaluate(expr.getRight());

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
                truthDistance = result ? rightNum - leftNum + K : 0;
                falseDistance = result ? 0 : leftNum - rightNum + K;
                break;
            case LESS_EQUALS:
                result = leftNum <= rightNum;
                truthDistance = result ? rightNum - leftNum + K : 0;
                falseDistance = result ? 0 : leftNum - rightNum + K;
                break;
            case GREATER:
                result = leftNum > rightNum;
                truthDistance = result ? leftNum - rightNum + K : 0;
                falseDistance = result ? 0 : rightNum - leftNum + K;
                break;
            case GREATER_EQUALS:
                result = leftNum >= rightNum;
                truthDistance = result ? leftNum - rightNum + K : 0;
                falseDistance = result ? 0 : rightNum - leftNum + K;
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
}
