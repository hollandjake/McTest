package com.github.hollandjake.com3529.generation.solver.mutation;

public class ShortInputMutator extends InputMutator<Short>
{
    @Override
    public Short generate()
    {
        return (short) (InputMutator.RANDOM().nextInt() % InputMutator.NUMBER_DISTRIBUTION().shortValue());
    }

    @Override
    public Short modify(Short value, double offset)
    {
        return (short) (value + (short) offset);
    }
}
