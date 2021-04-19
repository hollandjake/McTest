package com.github.hollandjake.com3529.generation.solver.mutation;

public class CharacterInputMutator implements InputMutator<Character>
{
    @Override
    public Character generate()
    {
        //Any ascii character
        return (char) (32 + RANDOM.nextInt(95));
    }

    @Override
    public Character modify(Character value, double offset)
    {
        //Any ascii character
        return (char) (32 + ((int) value - 32 + (int) offset) % (95));
    }
}
