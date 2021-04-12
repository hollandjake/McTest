package com.github.hollandjake.com3529.generation;

import java.util.Objects;

import com.github.hollandjake.com3529.generation.solver.fitness.FitnessMetric;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.typesafe.config.ConfigFactory;

import lombok.Data;

@Data
public class ConditionCoverage implements Cloneable
{
    public static final int K = ConfigFactory.load().getInt("ConditionCoverage.K");
    private final int conditionId;
    private final Boolean result;
    private final Double truthDistance;
    private final Double falseDistance;

    public static ConditionCoverage from(int conditionId, Object leftNum, Object rightNum, BinaryExpr.Operator operator)
    {
        FitnessMetric metric = FitnessMetric.getMetricFor(leftNum, rightNum);

        switch (operator)
        {
            case EQUALS:
                return metric.equals(conditionId, leftNum, rightNum);
            case NOT_EQUALS:
                return metric.notEquals(conditionId, leftNum, rightNum);
            case LESS:
                return metric.less(conditionId, leftNum, rightNum);
            case LESS_EQUALS:
                return metric.lessEquals(conditionId, leftNum, rightNum);
            case GREATER:
                return metric.greater(conditionId, leftNum, rightNum);
            case GREATER_EQUALS:
                return metric.greaterEquals(conditionId, leftNum, rightNum);
            default:
                throw new UnsupportedOperationException(String.format(
                        "Operation \"%s\" not supported!",
                        operator
                ));
        }
    }

    public static ConditionCoverage join(ConditionCoverage left, ConditionCoverage right)
    {
        if (left != null)
        {
            return left.join(right);
        }
        else if (right != null)
        {
            return right.clone();
        }
        else
        {
            return null;
        }
    }

    @Override
    public ConditionCoverage clone()
    {
        return new ConditionCoverage(this.conditionId, this.result, this.truthDistance, this.falseDistance);
    }

    public ConditionCoverage join(ConditionCoverage other)
    {
        if (other == null)
        {
            return this.clone();
        }

        int tempBranchNum;
        Boolean tempResult;
        double tempTruthDistance;
        double tempFalseDistance;

        if (this.conditionId != other.conditionId)
        {
            throw new UnsupportedOperationException("branchNum is not identical");
        }
        else
        {
            tempBranchNum = this.conditionId;
        }

        if (Objects.equals(this.result, other.result))
        {
            tempResult = this.result;
        }
        else
        {
            tempResult = null;
        }

        tempTruthDistance = Math.min(this.truthDistance, other.truthDistance);
        tempFalseDistance = Math.min(this.falseDistance, other.falseDistance);

        return new ConditionCoverage(tempBranchNum, tempResult, tempTruthDistance, tempFalseDistance);
    }

    public double getNormalisedFitness()
    {
        return normalise(truthDistance + falseDistance);
    }

    public static double normalise(double d)
    {
        return d / (d + 1);
    }
}
