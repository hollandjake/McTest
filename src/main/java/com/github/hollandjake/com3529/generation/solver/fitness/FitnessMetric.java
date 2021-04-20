package com.github.hollandjake.com3529.generation.solver.fitness;

import com.github.hollandjake.com3529.generation.ConditionCoverage;
import com.github.javaparser.ast.expr.BinaryExpr;

public abstract class FitnessMetric<T>
{
    public abstract ConditionCoverage equals(int conditionId, T left, T right);

    public abstract ConditionCoverage notEquals(int conditionId, T left, T right);

    public abstract ConditionCoverage less(int conditionId, T left, T right);

    public abstract ConditionCoverage lessEquals(int conditionId, T left, T right);

    public abstract ConditionCoverage greater(int conditionId, T left, T right);

    public abstract ConditionCoverage greaterEquals(int conditionId, T left, T right);

    public static FitnessMetric<Object> getMetricFor(Object leftVariable, Object rightVariable)
    {
        if (leftVariable instanceof Number)
        {
            if (!(rightVariable instanceof Number))
            {
                throw new UnsupportedOperationException(String.format(
                        "Class type mismatch <%s>, <%s>",
                        leftVariable.getClass(),
                        rightVariable.getClass()
                ));
            }
        }
        else if (leftVariable.getClass() != rightVariable.getClass())
        {
            throw new UnsupportedOperationException(String.format(
                    "Class type mismatch <%s>, <%s>",
                    leftVariable.getClass(),
                    rightVariable.getClass()
            ));
        }

        if (leftVariable instanceof Number)
        {
            return (FitnessMetric) new NumberFitnessMetric();
        }
        else if (leftVariable instanceof Boolean)
        {
            return (FitnessMetric) new BooleanFitnessMetric();
        }
        else if (leftVariable instanceof Character)
        {
            return (FitnessMetric) new CharacterFitnessMetric();
        }
        else if (leftVariable instanceof String)
        {
            return (FitnessMetric) new StringFitnessMetric();
        }
        else
        {
            throw new UnsupportedOperationException("Class type <" + leftVariable + "> not supported");
        }
    }

    static UnsupportedOperationException unsupported(Class<?> type, BinaryExpr.Operator operator)
    {
        return new UnsupportedOperationException(String.format(
                "Cannot compare <%s> with <%s> using %s",
                type, type, operator
        ));
    }
}
