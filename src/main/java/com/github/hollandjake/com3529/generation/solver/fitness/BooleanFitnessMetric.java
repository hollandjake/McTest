package com.github.hollandjake.com3529.generation.solver.fitness;

import java.util.Objects;

import com.github.hollandjake.com3529.generation.ConditionCoverage;
import com.github.javaparser.ast.expr.BinaryExpr;

import static com.github.hollandjake.com3529.generation.ConditionCoverage.K;

public class BooleanFitnessMetric implements FitnessMetric<Boolean>
{
    @Override
    public ConditionCoverage equals(int conditionId, Boolean left, Boolean right)
    {
        boolean result = Objects.equals(left, right);
        double truthDistance = result ? 0 : K;
        double falseDistance = result ? K : 0;
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    @Override
    public ConditionCoverage notEquals(int conditionId, Boolean left, Boolean right)
    {
        boolean result = !Objects.equals(left, right);
        double truthDistance = result ? 0 : K;
        double falseDistance = result ? K : 0;
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    @Override
    public ConditionCoverage less(int conditionId, Boolean left, Boolean right)
    {
        throw FitnessMetric.unsupported(Boolean.class, BinaryExpr.Operator.LESS);
    }

    @Override
    public ConditionCoverage lessEquals(int conditionId, Boolean left, Boolean right)
    {
        throw FitnessMetric.unsupported(Boolean.class, BinaryExpr.Operator.LESS_EQUALS);
    }

    @Override
    public ConditionCoverage greater(int conditionId, Boolean left, Boolean right)
    {
        throw FitnessMetric.unsupported(Boolean.class, BinaryExpr.Operator.GREATER);
    }

    @Override
    public ConditionCoverage greaterEquals(int conditionId, Boolean left, Boolean right)
    {
       throw FitnessMetric.unsupported(Boolean.class, BinaryExpr.Operator.GREATER_EQUALS);
    }
}
