package com.github.hollandjake.com3529.generation.solver.mutation;

public class ByteInputMutator extends InputMutator<Byte>
{
    @Override
    public Byte generate()
    {
        return (byte) (InputMutator.RANDOM().nextInt() % InputMutator.NUMBER_DISTRIBUTION().byteValue());
    }

    @Override
    public Byte modify(Byte value, double offset)
    {
        return (byte) (value + (byte) offset);
    }
}
