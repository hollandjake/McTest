package com.github.hollandjake.com3529.generation.solver.mutation;

/**
 * Class responsible for generating and mutating a {@link Short}
 */
public class ShortInputMutator extends InputMutator<Short>
{
    /**
     * Generate a random {@link Short}
     *
     * @return The random {@link Short}
     */
    @Override
    public Short generate()
    {
        return (short) (InputMutator.RANDOM().nextInt() % InputMutator.NUMBER_DISTRIBUTION().shortValue());
    }

    /**
     * Create a new individual which is a mutated version of the {@code value} argument, mutated by {@code offset} amount
     *
     * @param value The input to mutate
     * @param offset How much to mutate by
     * @return The new mutated individual
     */
    @Override
    public Short modify(Short value, double offset)
    {
        return (short) (value + (short) offset);
    }
}
