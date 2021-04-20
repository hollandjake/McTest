package com.github.hollandjake.com3529.generation.solver.fitness;

import java.util.Objects;

import com.github.hollandjake.com3529.generation.ConditionCoverage;

public class NumberFitnessMetric extends FitnessMetric<Number>
{
    @Override
    public ConditionCoverage equals(int conditionId, Number left, Number right)
    {
        boolean result = Objects.equals(left, right);
        double truthDistance = Math.abs(left.doubleValue() - right.doubleValue());
        double falseDistance = truthDistance == 0 ? ConditionCoverage.K() : 0;
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    @Override
    public ConditionCoverage notEquals(int conditionId, Number left, Number right)
    {
        boolean result = !Objects.equals(left, right);
        double falseDistance = Math.abs(left.doubleValue() - right.doubleValue());
        double truthDistance = falseDistance == 0 ? ConditionCoverage.K() : 0;
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    @Override
    public ConditionCoverage less(int conditionId, Number left, Number right)
    {
        boolean result = left.doubleValue() < right.doubleValue();
        double falseDistance = result ? right.doubleValue() - left.doubleValue() + ConditionCoverage.K() : 0;
        double truthDistance = result ? 0 : left.doubleValue() - right.doubleValue() + ConditionCoverage.K();
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    @Override
    public ConditionCoverage lessEquals(int conditionId, Number left, Number right)
    {
        boolean result = left.doubleValue() <= right.doubleValue();
        double falseDistance = result ? right.doubleValue() - left.doubleValue() + ConditionCoverage.K() : 0;
        double truthDistance = result ? 0 : left.doubleValue() - right.doubleValue() + ConditionCoverage.K();
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    @Override
    public ConditionCoverage greater(int conditionId, Number left, Number right)
    {
        boolean result = left.doubleValue() > right.doubleValue();
        double falseDistance = result ? left.doubleValue() - right.doubleValue() + ConditionCoverage.K() : 0;
        double truthDistance = result ? 0 : right.doubleValue() - left.doubleValue() + ConditionCoverage.K();
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    @Override
    public ConditionCoverage greaterEquals(int conditionId, Number left, Number right)
    {
        boolean result = left.doubleValue() >= right.doubleValue();
        double falseDistance = result ? left.doubleValue() - right.doubleValue() + ConditionCoverage.K() : 0;
        double truthDistance = result ? 0 : right.doubleValue() - left.doubleValue() + ConditionCoverage.K();
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }
}
