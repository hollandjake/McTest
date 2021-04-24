package com.github.hollandjake.com3529.generation.solver.mutation;

/**
 * Class responsible for generating and mutating a {@link Integer}
 */
public class IntegerInputMutator extends InputMutator<Integer>
{
    /**
     * Generate a random {@link Integer}
     *
     * @return The random {@link Integer}
     */
    @Override
    public Integer generate()
    {
        return InputMutator.RANDOM().nextInt() % InputMutator.NUMBER_DISTRIBUTION().intValue();
    }

    /**
     * Create a new individual which is a mutated version of the {@code value} argument, mutated by {@code offset} amount
     *
     * @param value The input to mutate
     * @param offset How much to mutate by
     * @return The new mutated individual
     */
    @Override
    public Integer modify(Integer value, double offset)
    {
        return value + (int) offset;
    }
}
