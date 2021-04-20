package com.github.hollandjake.com3529.generation.solver.mutation;

public class BooleanInputMutator extends InputMutator<Boolean>
{
    @Override
    public Boolean generate()
    {
        return InputMutator.RANDOM().nextBoolean();
    }

    @Override
    public Boolean modify(Boolean value, double offset)
    {
        return ((value ? 1 : 0) + offset) % 2 != 0;
    }
}
