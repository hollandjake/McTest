package com.github.hollandjake.com3529.generation.solver.mutation;

public class FloatInputMutator implements InputMutator<Float>
{
    @Override
    public Float generate()
    {
        return RANDOM.nextFloat() * NUMBER_DISTRIBUTION.floatValue();
    }

    @Override
    public Float modify(Float value, double offset)
    {
        return value + (float) offset;
    }
}
