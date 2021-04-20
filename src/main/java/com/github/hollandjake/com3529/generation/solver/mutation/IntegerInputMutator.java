package com.github.hollandjake.com3529.generation.solver.mutation;

public class IntegerInputMutator extends InputMutator<Integer>
{
    @Override
    public Integer generate()
    {
        return InputMutator.RANDOM().nextInt() % InputMutator.NUMBER_DISTRIBUTION().intValue();
    }

    @Override
    public Integer modify(Integer value, double offset)
    {
        return value + (int) offset;
    }
}
