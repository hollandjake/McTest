package com.github.hollandjake.com3529.generation.solver.mutation;

/**
 * Class responsible for generating and mutating a {@link Boolean}
 */
public class BooleanInputMutator extends InputMutator<Boolean>
{
    /**
     * Generate a random {@link Boolean}
     *
     * @return The random {@link Boolean}
     */
    @Override
    public Boolean generate()
    {
        return InputMutator.RANDOM().nextBoolean();
    }

    /**
     * Create a new individual which is a mutated version of the {@code value} argument, mutated by {@code offset} amount
     *
     * @param value The input to mutate
     * @param offset How much to mutate by
     * @return The new mutated individual
     */
    @Override
    public Boolean modify(Boolean value, double offset)
    {
        return ((value ? 1 : 0) + offset) % 2 != 0;
    }
}
