package com.github.hollandjake.com3529.generation.solver.mutation;

/**
 * Class responsible for generating and mutating a {@link Character}
 */
public class CharacterInputMutator extends InputMutator<Character>
{
    /**
     * Generate a random {@link Character}
     *
     * @return The random {@link Character}
     */
    @Override
    public Character generate()
    {
        //Any ascii character
        return (char) (32 + InputMutator.RANDOM().nextInt(95));
    }

    /**
     * Create a new individual which is a mutated version of the {@code value} argument, mutated by {@code offset} amount
     *
     * @param value The input to mutate
     * @param offset How much to mutate by
     * @return The new mutated individual
     */
    @Override
    public Character modify(Character value, double offset)
    {
        //Any ascii character
        return (char) (32 + ((int) value - 32 + (int) offset) % (95));
    }
}
