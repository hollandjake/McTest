package com.github.hollandjake.com3529.generation.solver.mutation;

public class ByteInputMutator implements InputMutator<Byte>
{
    @Override
    public Byte generate()
    {
        return (byte) (RANDOM.nextInt() % NUMBER_DISTRIBUTION.byteValue());
    }

    @Override
    public Byte modify(Byte value, double offset)
    {
        return (byte) (value + (byte) offset);
    }
}
