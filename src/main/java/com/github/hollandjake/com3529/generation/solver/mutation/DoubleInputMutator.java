package com.github.hollandjake.com3529.generation.solver.mutation;

/**
 * Class responsible for generating and mutating a {@link Double}
 */
public class DoubleInputMutator extends InputMutator<Double>
{
    /**
     * Generate a random {@link Double}
     *
     * @return The random {@link Double}
     */
    @Override
    public Double generate()
    {
        return InputMutator.RANDOM().nextDouble() * InputMutator.NUMBER_DISTRIBUTION().doubleValue();
    }

    /**
     * Create a new individual which is a mutated version of the {@code value} argument, mutated by {@code offset} amount
     *
     * @param value  The input to mutate
     * @param offset How much to mutate by
     * @return The new mutated individual
     */
    @Override
    public Double modify(Double value, double offset)
    {
        return value + offset;
    }
}
