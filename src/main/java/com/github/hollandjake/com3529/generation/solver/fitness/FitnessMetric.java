package com.github.hollandjake.com3529.generation.solver.fitness;

import com.github.hollandjake.com3529.generation.ConditionCoverage;
import com.github.javaparser.ast.expr.BinaryExpr;

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
        if (leftVariable instanceof Number) {
            if (!(rightVariable instanceof Number)) {
                throw new UnsupportedOperationException(String.format(
                        "Class type mismatch <%s>, <%s>",
                        leftVariable.getClass(),
                        rightVariable.getClass()
                ));
            }
        } else if (leftVariable.getClass() != rightVariable.getClass()) {
            throw new UnsupportedOperationException(String.format(
                    "Class type mismatch <%s>, <%s>",
                    leftVariable.getClass(),
                    rightVariable.getClass()
            ));
        }

        if (leftVariable instanceof Number)
        {
            return new NumberFitnessMetric();
        } else if (leftVariable instanceof Boolean) {
            return new BooleanFitnessMetric();
        } else if (leftVariable instanceof Character) {
            return new CharacterFitnessMetric();
        } else if (leftVariable instanceof String) {
            return new StringFitnessMetric();
        }
        else
        {
            throw new UnsupportedOperationException("Class type <" + leftVariable + "> not supported");
        }
    }

    static UnsupportedOperationException unsupported(Class<?> type, BinaryExpr.Operator operator) {
        return new UnsupportedOperationException(String.format(
                "Cannot compare <%s> with <%s> using %s",
                type,type,operator
        ));
    }
}
