package com.github.hollandjake.com3529.generation.solver.mutation;

/**
 * Class responsible for generating and mutating a {@link Long}
 */
public class LongInputMutator extends InputMutator<Long>
{
    /**
     * Generate a random {@link Long}
     *
     * @return The random {@link Long}
     */
    @Override
    public Long generate()
    {
        return InputMutator.RANDOM().nextLong() % InputMutator.NUMBER_DISTRIBUTION().longValue();
    }

    /**
     * Create a new individual which is a mutated version of the {@code value} argument, mutated by {@code offset} amount
     *
     * @param value  The input to mutate
     * @param offset How much to mutate by
     * @return The new mutated individual
     */
    @Override
    public Long modify(Long value, double offset)
    {
        return value + (long) offset;
    }
}
