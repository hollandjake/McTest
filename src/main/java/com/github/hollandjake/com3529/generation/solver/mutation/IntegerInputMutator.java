package com.github.hollandjake.com3529.generation.solver.mutation;

public class IntegerInputMutator implements InputMutator<Integer>
{
    @Override
    public Integer generate()
    {
        return RANDOM.nextInt() % NUMBER_DISTRIBUTION.intValue();
    }

    @Override
    public Integer modify(Integer value, double offset)
    {
        return value + (int) offset;
    }
}
