package com.github.hollandjake.com3529.generation.solver.mutation;

public class DoubleInputMutator implements InputMutator<Double>
{
    @Override
    public Double generate()
    {
        return RANDOM.nextDouble() * NUMBER_DISTRIBUTION.doubleValue();
    }

    @Override
    public Double modify(Double value, double offset)
    {
        return value + offset;
    }
}
