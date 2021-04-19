package com.github.hollandjake.com3529.generation.solver.mutation;

public class ShortInputMutator implements InputMutator<Short>
{
    @Override
    public Short generate()
    {
        return (short) (RANDOM.nextInt() % NUMBER_DISTRIBUTION.shortValue());
    }

    @Override
    public Short modify(Short value, double offset)
    {
        return (short) (value + (short) offset);
    }
}
