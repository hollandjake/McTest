package com.github.hollandjake.com3529.generation.solver.fitness;

import com.github.hollandjake.com3529.generation.ConditionCoverage;

public interface FitnessMetric<T>
{
    ConditionCoverage equals(int conditionId, T left, T right);

    ConditionCoverage notEquals(int conditionId, T left, T right);

    ConditionCoverage less(int conditionId, T left, T right);

    ConditionCoverage lessEquals(int conditionId, T left, T right);

    ConditionCoverage greater(int conditionId, T left, T right);

    ConditionCoverage greaterEquals(int conditionId, T left, T right);

    static FitnessMetric getMetricFor(Object leftVariable, Object rightVariable)
    {
        if ((leftVariable instanceof Number) ^ (rightVariable instanceof Number))
        {
            throw new UnsupportedOperationException(String.format(
                    "Class type mismatch <%s>, <%s>",
                    leftVariable,
                    rightVariable
            ));
        }

        if (leftVariable instanceof Number)
        {
            return new NumberFitnessMetric();
        }
        else
        {
            throw new UnsupportedOperationException("Class type <" + leftVariable + "> not supported");
        }
    }
}
