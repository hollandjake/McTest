package com.github.hollandjake.com3529.genetics;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;

import lombok.Data;

@Data
public class Fitness
{
    private final Boolean result;
    private final Float truthScore;

    public static Fitness forExpression(Expression expression)
    {
        if (expression instanceof BinaryExpr) {
            return evaluateBinaryExpr((BinaryExpr) expression);
        }
        return null;
    }

    private static Fitness evaluateBinaryExpr(BinaryExpr expression) {
        Expression left = expression.getLeft();
        Expression right = expression.getRight();
        BinaryExpr.Operator operator = expression.getOperator();

        //Must be of a numerical type
        Float leftNumber = Float.valueOf(left.asStringLiteralExpr().asString());
        Float rightNumber = Float.valueOf(right.asStringLiteralExpr().asString());

        boolean result;
        float truthScore;
        float scaleOffset = Math.abs(rightNumber) + Math.abs(leftNumber);

        switch (operator) {
            case GREATER:
                result = leftNumber > rightNumber;
                truthScore = rightNumber - leftNumber + scaleOffset;
                return new Fitness(result, truthScore);
            case EQUALS:
                result = leftNumber.equals(rightNumber);
                truthScore = Math.abs(rightNumber - leftNumber) + scaleOffset;
                return new Fitness(result, truthScore);
            case LESS_EQUALS:
                result = leftNumber <= rightNumber;
                truthScore = leftNumber - rightNumber + scaleOffset;
                return new Fitness(result, truthScore);
            default:
                throw new RuntimeException("Operator "+operator.toString()+" is not supported");
        }
    }
}
