package com.github.hollandjake.com3529.generation.solver.fitness;

import com.github.hollandjake.com3529.generation.ConditionCoverage;
import com.github.javaparser.ast.expr.BinaryExpr;

/**
 * Fitness evaluator factory
 * <p>
 * Responsible for assigning the correct metric calculator given two inputs
 */
public abstract class FitnessMetric<T>
{
    /**
     * Equality - fitness evaluation between two {@link T} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left        left argument of the operation
     * @param right       right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    public abstract ConditionCoverage equals(int conditionId, T left, T right);

    /**
     * Inverse equality - fitness evaluation between two {@link T} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left        left argument of the operation
     * @param right       right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    public abstract ConditionCoverage notEquals(int conditionId, T left, T right);

    /**
     * Less than - fitness evaluation between two {@link T} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left        left argument of the operation
     * @param right       right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    public abstract ConditionCoverage less(int conditionId, T left, T right);

    /**
     * Less than or equal to - fitness evaluation between two {@link T} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left        left argument of the operation
     * @param right       right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    public abstract ConditionCoverage lessEquals(int conditionId, T left, T right);

    /**
     * Greater than - fitness evaluation between two {@link T} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left        left argument of the operation
     * @param right       right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    public abstract ConditionCoverage greater(int conditionId, T left, T right);

    /**
     * Greater than or equal to - fitness evaluation between two {@link Boolean} inputs
     *
     * @param conditionId the condition this is executing under
     * @param left        left argument of the operation
     * @param right       right argument of the operation
     * @return {@link ConditionCoverage} containing the evaluated fitness for that conditional statement
     */
    public abstract ConditionCoverage greaterEquals(int conditionId, T left, T right);

    /**
     * Calculated the correct fitness metric to use given two {@link Object inputs}
     *
     * @param leftVariable  The first argument
     * @param rightVariable The second argument
     * @return {@link FitnessMetric<Object>} The fitness metric instance associated with the two inputs
     * @throws UnsupportedOperationException When both inputs dont match types
     * @throws UnsupportedOperationException When both input types are not supported
     */
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
            return (FitnessMetric) new NumberFitnessMetric();
        }
        else if (leftVariable.getClass() != rightVariable.getClass())
        {
            throw new UnsupportedOperationException(String.format(
                    "Class type mismatch <%s>, <%s>",
                    leftVariable.getClass(),
                    rightVariable.getClass()
            ));
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

    /**
     * Generate an {@link UnsupportedOperationException} relating to an input type and an operator
     *
     * @param type     The type of input
     * @param operator The type of operator
     * @return The generated {@link UnsupportedOperationException}
     */
    static UnsupportedOperationException unsupported(Class<?> type, BinaryExpr.Operator operator)
    {
        return new UnsupportedOperationException(String.format(
                "Cannot compare <%s> with <%s> using %s",
                type, type, operator
        ));
    }
}
