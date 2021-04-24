package com.github.hollandjake.com3529.generation.solver.mutation;

/**
 * Class responsible for generating and mutating a {@link Float}
 */
public class FloatInputMutator extends InputMutator<Float>
{
    /**
     * Generate a random {@link Float}
     *
     * @return The random {@link Float}
     */
    @Override
    public Float generate()
    {
        return InputMutator.RANDOM().nextFloat() * InputMutator.NUMBER_DISTRIBUTION().floatValue();
    }

    /**
     * Create a new individual which is a mutated version of the {@code value} argument, mutated by {@code offset} amount
     *
     * @param value The input to mutate
     * @param offset How much to mutate by
     * @return The new mutated individual
     */
    @Override
    public Float modify(Float value, double offset)
    {
        return value + (float) offset;
    }
}
