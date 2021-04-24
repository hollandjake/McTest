package com.github.hollandjake.com3529.generation.solver.fitness;

import java.util.Objects;

import com.github.hollandjake.com3529.generation.ConditionCoverage;

/**
 * Fitness evaluator for {@link Character} and its primitive
 */
public class CharacterFitnessMetric extends FitnessMetric<Character>
{
    /**
     * Equality - fitness evaluation between two {@link Character} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left left argument of the operation
     * @param right right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    @Override
    public ConditionCoverage equals(int conditionId, Character left, Character right)
    {
        boolean result = Objects.equals(left, right);
        int leftNum = (int) left;
        int rightNum = (int) right;
        double truthDistance = Math.abs(leftNum - rightNum);
        double falseDistance = truthDistance == 0 ? ConditionCoverage.K() : 0;
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    /**
     * Inverse equality - fitness evaluation between two {@link Character} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left left argument of the operation
     * @param right right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    @Override
    public ConditionCoverage notEquals(int conditionId, Character left, Character right)
    {
        boolean result = !Objects.equals(left, right);
        int leftNum = (int) left;
        int rightNum = (int) right;
        double falseDistance = Math.abs(leftNum - rightNum);
        double truthDistance = falseDistance == 0 ? ConditionCoverage.K() : 0;
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    /**
     * Less than - fitness evaluation between two {@link Character} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left left argument of the operation
     * @param right right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    @Override
    public ConditionCoverage less(int conditionId, Character left, Character right)
    {
        boolean result = left < right;
        int leftNum = (int) left;
        int rightNum = (int) right;
        double falseDistance = result ? rightNum - leftNum + ConditionCoverage.K() : 0;
        double truthDistance = result ? 0 : leftNum - rightNum + ConditionCoverage.K();
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    /**
     * Less than or equal to - fitness evaluation between two {@link Character} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left left argument of the operation
     * @param right right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    @Override
    public ConditionCoverage lessEquals(int conditionId, Character left, Character right)
    {
        boolean result = left <= right;
        int leftNum = (int) left;
        int rightNum = (int) right;
        double falseDistance = result ? rightNum - leftNum + ConditionCoverage.K() : 0;
        double truthDistance = result ? 0 : leftNum - rightNum + ConditionCoverage.K();
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    /**
     * Greater than - fitness evaluation between two {@link Character} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left left argument of the operation
     * @param right right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    @Override
    public ConditionCoverage greater(int conditionId, Character left, Character right)
    {
        boolean result = left > right;
        int leftNum = (int) left;
        int rightNum = (int) right;
        double falseDistance = result ? leftNum - rightNum + ConditionCoverage.K() : 0;
        double truthDistance = result ? 0 : rightNum - leftNum + ConditionCoverage.K();
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }

    /**
     * Greater than or equal to - fitness evaluation between two {@link Character} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left left argument of the operation
     * @param right right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    @Override
    public ConditionCoverage greaterEquals(int conditionId, Character left, Character right)
    {
        boolean result = left >= right;
        int leftNum = (int) left;
        int rightNum = (int) right;
        double falseDistance = result ? leftNum - rightNum + ConditionCoverage.K() : 0;
        double truthDistance = result ? 0 : rightNum - leftNum + ConditionCoverage.K();
        return new ConditionCoverage(conditionId, result, truthDistance, falseDistance);
    }
}
