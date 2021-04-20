package com.github.hollandjake.com3529.generation.solver.fitness;

import java.util.Objects;

import com.github.hollandjake.com3529.generation.ConditionCoverage;
import com.github.javaparser.ast.expr.BinaryExpr;

public class StringFitnessMetric extends FitnessMetric<String>
{
    @Override
    public ConditionCoverage equals(int conditionId, String left, String right)
    {
        boolean result = Objects.equals(left, right);
        long distance = distance(left, right);
        double truthDistance = distance;
        double falseDistance = truthDistance == 0 ? ConditionCoverage.K() : 0;
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    @Override
    public ConditionCoverage notEquals(int conditionId, String left, String right)
    {
        boolean result = !Objects.equals(left, right);
        long distance = distance(left, right);
        double falseDistance = distance;
        double truthDistance = falseDistance == 0 ? ConditionCoverage.K() : 0;
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    @Override
    public ConditionCoverage less(int conditionId, String left, String right)
    {
        throw FitnessMetric.unsupported(String.class, BinaryExpr.Operator.LESS);
    }

    @Override
    public ConditionCoverage lessEquals(int conditionId, String left, String right)
    {
        throw FitnessMetric.unsupported(String.class, BinaryExpr.Operator.LESS_EQUALS);
    }

    @Override
    public ConditionCoverage greater(int conditionId, String left, String right)
    {
        throw FitnessMetric.unsupported(String.class, BinaryExpr.Operator.GREATER);
    }

    @Override
    public ConditionCoverage greaterEquals(int conditionId, String left, String right)
    {
        throw FitnessMetric.unsupported(Boolean.class, BinaryExpr.Operator.GREATER_EQUALS);
    }

    private long distance(String left, String right)
    {
        long totalDist = 0;

        //Add string length difference
        int minLength = Math.min(left.length(), right.length());
        int maxLength = Math.max(left.length(), right.length());
        totalDist += (maxLength - minLength) * 65535L;

        for (int i = 0; i < minLength; i++)
        {
            totalDist += 1 - 1 / (1 + Math.abs(left.charAt(i) - right.charAt(i)));
        }

        return totalDist;
    }
}
