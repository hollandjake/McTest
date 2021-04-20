package com.github.hollandjake.com3529.generation.solver.fitness;

import java.util.Objects;

import com.github.hollandjake.com3529.generation.ConditionCoverage;

public class CharacterFitnessMetric extends FitnessMetric<Character>
{
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
