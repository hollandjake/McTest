package com.github.hollandjake.com3529.generation;

import java.util.Objects;

import com.github.hollandjake.com3529.generation.solver.fitness.FitnessMetric;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.typesafe.config.ConfigFactory;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Responsible for processing the injected condition calls and computing the fitness metrics for that condition,
 * the returned instance represents all the information about the fitness of that particular invocation
 */
@Data
public class ConditionCoverage implements Cloneable
{
    /**
     * K represents the constant factor when computing fitness metrics
     */
    @Getter
    @Accessors(fluent = true)
    private static final int K = ConfigFactory.load().getInt("ConditionCoverage.K");

    private final int conditionId;
    private final Boolean result;
    private final Double truthDistance;
    private final Double falseDistance;

    /**
     * Compute the coverage for a condition based on the two arguments and the operator
     *
     * @param conditionId  The condition id that is being executed
     * @param leftOperand  the left operand
     * @param rightOperand the right operand
     * @param operator     the operator
     * @return The coverage for that conditions execution
     */
    public static ConditionCoverage from(int conditionId,
            Object leftOperand,
            Object rightOperand,
            BinaryExpr.Operator operator)
    {
        FitnessMetric<Object> metric = FitnessMetric.getMetricFor(leftOperand, rightOperand);

        switch (operator)
        {
            case EQUALS:
                return metric.equals(conditionId, leftOperand, rightOperand);
            case NOT_EQUALS:
                return metric.notEquals(conditionId, leftOperand, rightOperand);
            case LESS:
                return metric.less(conditionId, leftOperand, rightOperand);
            case LESS_EQUALS:
                return metric.lessEquals(conditionId, leftOperand, rightOperand);
            case GREATER:
                return metric.greater(conditionId, leftOperand, rightOperand);
            case GREATER_EQUALS:
                return metric.greaterEquals(conditionId, leftOperand, rightOperand);
            default:
                throw new UnsupportedOperationException(String.format(
                        "Operation \"%s\" not supported!",
                        operator
                ));
        }
    }

    /**
     * Combine two {@link ConditionCoverage} elements together
     * @param left First {@link ConditionCoverage}
     * @param right Second {@link ConditionCoverage}
     * @return The joined {@link ConditionCoverage}
     */
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

    /**
     * Perform a deep copy of the {@link ConditionCoverage} element
     * @return The cloned instance
     */
    @Override
    public ConditionCoverage clone()
    {
        return new ConditionCoverage(this.conditionId, this.result, this.truthDistance, this.falseDistance);
    }

    /**
     * Join a {@link ConditionCoverage} to this {@link ConditionCoverage}
     * @param other The other {@link ConditionCoverage}
     * @return The new joined {@link ConditionCoverage}
     */
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

    /**
     * Calculates the normalise fitness.
     * This value will be between 0 and 1
     * @return The normalised fitness
     */
    public double getNormalisedFitness()
    {
        return normalise(truthDistance + falseDistance);
    }

    /**
     * Normalise any {@link Double} into a value between 0 and 1
     * @param d The value to normalise
     * @return The normalised value
     */
    public static double normalise(double d)
    {
        return d / (d + 1);
    }
}
