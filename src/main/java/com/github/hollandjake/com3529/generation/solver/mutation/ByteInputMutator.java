package com.github.hollandjake.com3529.generation.solver.mutation;

/**
 * Class responsible for generating and mutating a {@link Byte}
 */
public class ByteInputMutator extends InputMutator<Byte>
{
    /**
     * Generate a random {@link Byte}
     *
     * @return The random {@link Byte}
     */
    @Override
    public Byte generate()
    {
        return (byte) (InputMutator.RANDOM().nextInt() % InputMutator.NUMBER_DISTRIBUTION().byteValue());
    }

    /**
     * Create a new individual which is a mutated version of the {@code value} argument, mutated by {@code offset} amount
     *
     * @param value The input to mutate
     * @param offset How much to mutate by
     * @return The new mutated individual
     */
    @Override
    public Byte modify(Byte value, double offset)
    {
        return (byte) (value + (byte) offset);
    }
}
