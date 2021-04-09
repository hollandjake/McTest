package com.github.hollandjake.com3529.generation.solver.fitness;

import java.util.Objects;

import com.github.hollandjake.com3529.generation.BranchCoverage;

import static com.github.hollandjake.com3529.generation.BranchCoverage.K;

public class NumberFitnessMetric implements FitnessMetric<Double>
{
    @Override
    public BranchCoverage equals(int conditionId, Double left, Double right)
    {
        boolean result = Objects.equals(left, right);
        double truthDistance = Math.abs(left - right);
        double falseDistance = truthDistance == 0 ? K : 0;
        return new BranchCoverage(conditionId, result, truthDistance, falseDistance);
    }

    @Override
    public BranchCoverage notEquals(int conditionId, Double left, Double right)
    {
        boolean result = !Objects.equals(left, right);
        double falseDistance = Math.abs(left - right);
        double truthDistance = falseDistance == 0 ? K : 0;
        return new BranchCoverage(conditionId, result, truthDistance, falseDistance);
    }

    @Override
    public BranchCoverage less(int conditionId, Double left, Double right)
    {
        boolean result = left < right;
        double falseDistance = result ? right - left + K : 0;
        double truthDistance = result ? 0 : left - right + K;
        return new BranchCoverage(conditionId, result, truthDistance, falseDistance);
    }

    @Override
    public BranchCoverage lessEquals(int conditionId, Double left, Double right)
    {
        boolean result = left <= right;
        double falseDistance = result ? right - left + K : 0;
        double truthDistance = result ? 0 : left - right + K;
        return new BranchCoverage(conditionId, result, truthDistance, falseDistance);
    }

    @Override
    public BranchCoverage greater(int conditionId, Double left, Double right)
    {
        boolean result = left > right;
        double falseDistance = result ? left - right + K : 0;
        double truthDistance = result ? 0 : right - left + K;
        return new BranchCoverage(conditionId, result, truthDistance, falseDistance);
    }

    @Override
    public BranchCoverage greaterEquals(int conditionId, Double left, Double right)
    {
        boolean result = left >= right;
        double falseDistance = result ? left - right + K : 0;
        double truthDistance = result ? 0 : right - left + K;
        return new BranchCoverage(conditionId, result, truthDistance, falseDistance);
    }
}
