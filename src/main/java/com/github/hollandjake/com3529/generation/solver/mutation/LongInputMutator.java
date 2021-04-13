package com.github.hollandjake.com3529.generation.solver.mutation;

public class LongInputMutator implements InputMutator<Long>
{
    @Override
    public Long generate()
    {
        return RANDOM.nextLong() % NUMBER_DISTRIBUTION.longValue();
    }

    @Override
    public Long modify(Long value, double offset)
    {
        return value + (long) offset;
    }
}
