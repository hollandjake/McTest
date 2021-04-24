package com.github.hollandjake.com3529.generation.solver.fitness;

import java.util.Objects;

import com.github.hollandjake.com3529.generation.ConditionCoverage;
import com.github.javaparser.ast.expr.BinaryExpr;

/**
 * Fitness evaluator for {@link Boolean} and its primitive
 */
public class BooleanFitnessMetric extends FitnessMetric<Boolean>
{
    /**
     * Equality - fitness evaluation between two {@link Boolean} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left        left argument of the operation
     * @param right       right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    @Override
    public ConditionCoverage equals(int conditionId, Boolean left, Boolean right)
    {
        boolean result = Objects.equals(left, right);
        double truthDistance = result ? 0 : ConditionCoverage.K();
        double falseDistance = result ? ConditionCoverage.K() : 0;
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    /**
     * Inverse equality - fitness evaluation between two {@link Boolean} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left        left argument of the operation
     * @param right       right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    @Override
    public ConditionCoverage notEquals(int conditionId, Boolean left, Boolean right)
    {
        boolean result = !Objects.equals(left, right);
        double truthDistance = result ? 0 : ConditionCoverage.K();
        double falseDistance = result ? ConditionCoverage.K() : 0;
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    /**
     * Less than - fitness evaluation between two {@link Boolean} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left        left argument of the operation
     * @param right       right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    @Override
    public ConditionCoverage less(int conditionId, Boolean left, Boolean right)
    {
        throw FitnessMetric.unsupported(Boolean.class, BinaryExpr.Operator.LESS);
    }

    /**
     * Less than or equal to - fitness evaluation between two {@link Boolean} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left        left argument of the operation
     * @param right       right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    @Override
    public ConditionCoverage lessEquals(int conditionId, Boolean left, Boolean right)
    {
        throw FitnessMetric.unsupported(Boolean.class, BinaryExpr.Operator.LESS_EQUALS);
    }

    /**
     * Greater than - fitness evaluation between two {@link Boolean} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left        left argument of the operation
     * @param right       right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    @Override
    public ConditionCoverage greater(int conditionId, Boolean left, Boolean right)
    {
        throw FitnessMetric.unsupported(Boolean.class, BinaryExpr.Operator.GREATER);
    }

    /**
     * Greater than or equal to - fitness evaluation between two {@link Boolean} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left        left argument of the operation
     * @param right       right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    @Override
    public ConditionCoverage greaterEquals(int conditionId, Boolean left, Boolean right)
    {
        throw FitnessMetric.unsupported(Boolean.class, BinaryExpr.Operator.GREATER_EQUALS);
    }
}
