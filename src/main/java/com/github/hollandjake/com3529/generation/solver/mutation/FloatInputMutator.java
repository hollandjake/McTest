package com.github.hollandjake.com3529.generation.solver.mutation;

public class FloatInputMutator extends InputMutator<Float>
{
    @Override
    public Float generate()
    {
        return InputMutator.RANDOM().nextFloat() * InputMutator.NUMBER_DISTRIBUTION().floatValue();
    }

    @Override
    public Float modify(Float value, double offset)
    {
        return value + (float) offset;
    }
}
