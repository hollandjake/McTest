package com.github.hollandjake.com3529.generation.solver.fitness;

import java.util.Objects;

import com.github.hollandjake.com3529.generation.ConditionCoverage;
import com.github.javaparser.ast.expr.BinaryExpr;

/**
 * Fitness evaluator for {@link String}
 */
public class StringFitnessMetric extends FitnessMetric<String>
{
    /**
     * Equality - fitness evaluation between two {@link String} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left left argument of the operation
     * @param right right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    @Override
    public ConditionCoverage equals(int conditionId, String left, String right)
    {
        boolean result = Objects.equals(left, right);
        long distance = distance(left, right);
        double truthDistance = distance;
        double falseDistance = truthDistance == 0 ? ConditionCoverage.K() : 0;
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    /**
     * Inverse equality - fitness evaluation between two {@link String} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left left argument of the operation
     * @param right right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    @Override
    public ConditionCoverage notEquals(int conditionId, String left, String right)
    {
        boolean result = !Objects.equals(left, right);
        long distance = distance(left, right);
        double falseDistance = distance;
        double truthDistance = falseDistance == 0 ? ConditionCoverage.K() : 0;
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    /**
     * Less than - fitness evaluation between two {@link String} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left left argument of the operation
     * @param right right argument of the operation
     * @throws UnsupportedOperationException Strings cannot be compared using less-than operator "<"
     */
    @Override
    public ConditionCoverage less(int conditionId, String left, String right)
    {
        throw FitnessMetric.unsupported(String.class, BinaryExpr.Operator.LESS);
    }

    /**
     * Less than or equal to - fitness evaluation between two {@link String} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left left argument of the operation
     * @param right right argument of the operation
     * @throws UnsupportedOperationException Strings cannot be compared using less-than-or-equal-to "<="
     */
    @Override
    public ConditionCoverage lessEquals(int conditionId, String left, String right)
    {
        throw FitnessMetric.unsupported(String.class, BinaryExpr.Operator.LESS_EQUALS);
    }

    /**
     * Greater than - fitness evaluation between two {@link String} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left left argument of the operation
     * @param right right argument of the operation
     * @throws UnsupportedOperationException Strings cannot be compared using greater-than ">"
     */
    @Override
    public ConditionCoverage greater(int conditionId, String left, String right)
    {
        throw FitnessMetric.unsupported(String.class, BinaryExpr.Operator.GREATER);
    }

    /**
     * Greater than or equal to - fitness evaluation between two {@link String} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left left argument of the operation
     * @param right right argument of the operation
     * @throws UnsupportedOperationException Strings cannot be compared using greater-than-or-equal-to ">="
     */
    @Override
    public ConditionCoverage greaterEquals(int conditionId, String left, String right)
    {
        throw FitnessMetric.unsupported(Boolean.class, BinaryExpr.Operator.GREATER_EQUALS);
    }

    /**
     * Calculates the distance between two {@link String Strings}
     *
     * @param left Input {@link String}
     * @param right Comparison {@link String}
     * @return distance between the two strings
     */
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
