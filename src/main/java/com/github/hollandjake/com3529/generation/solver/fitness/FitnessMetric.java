package com.github.hollandjake.com3529.generation.solver.fitness;

import com.github.hollandjake.com3529.generation.BranchCoverage;

public interface FitnessMetric<T>
{
    BranchCoverage equals(int conditionId, T left, T right);

    BranchCoverage notEquals(int conditionId, T left, T right);

    BranchCoverage less(int conditionId, T left, T right);

    BranchCoverage lessEquals(int conditionId, T left, T right);

    BranchCoverage greater(int conditionId, T left, T right);

    BranchCoverage greaterEquals(int conditionId, T left, T right);

    static FitnessMetric getMetricFor(Object leftVariable, Object rightVariable)
    {
        Class<?> type = leftVariable.getClass();
        if (type != rightVariable.getClass())
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
