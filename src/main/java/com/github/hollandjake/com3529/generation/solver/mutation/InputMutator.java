package com.github.hollandjake.com3529.generation.solver.mutation;

import java.util.Random;

import com.typesafe.config.ConfigFactory;

public interface InputMutator<T>
{
    Random RANDOM = new Random();
    Number NUMBER_DISTRIBUTION = ConfigFactory.load().getNumber("Genetics.Initial.InputDistribution");

    IntegerInputMutator integerInputMutator = new IntegerInputMutator();
    FloatInputMutator floatInputMutator = new FloatInputMutator();
    DoubleInputMutator doubleInputMutator = new DoubleInputMutator();
    LongInputMutator longInputMutator = new LongInputMutator();
    BooleanInputMutator booleanInputMutator = new BooleanInputMutator();
    CharacterInputMutator characterInputMutator = new CharacterInputMutator();
    StringInputMutator stringInputMutator = new StringInputMutator();

    T generate();

    T modify(T value, double offset);

    static Object generate(Class<?> type)
    {
        if (type == Integer.class || type == int.class)
        {
            return integerInputMutator.generate();
        }
        else if (type == Float.class || type == float.class)
        {
            return floatInputMutator.generate();
        }
        else if (type == Double.class || type == double.class)
        {
            return doubleInputMutator.generate();
        }
        else if (type == Long.class || type == long.class)
        {
            return longInputMutator.generate();
        }
        else if (type == Boolean.class || type == boolean.class)
        {
            return booleanInputMutator.generate();
        }
        else if (type == Character.class || type == char.class)
        {
            return characterInputMutator.generate();
        }
        else if (type == String.class)
        {
            return stringInputMutator.generate();
        }
        else
        {
            throw new UnsupportedOperationException("Class type <" + type + "> not supported");
        }
    }

    static Object add(Object input, double offset)
    {
        if (input instanceof Integer)
        {
            return integerInputMutator.modify((Integer) input, offset);
        }
        else if (input instanceof Float)
        {
            return floatInputMutator.modify((Float) input, offset);
        }
        else if (input instanceof Double)
        {
            return doubleInputMutator.modify((Double) input, offset);
        }
        else if (input instanceof Long)
        {
            return longInputMutator.modify((Long) input, offset);
        }
        else if (input instanceof Boolean)
        {
            return booleanInputMutator.modify((Boolean) input, offset);
        }
        else if (input instanceof Character)
        {
            return characterInputMutator.modify((Character) input, offset);
        }
        else if (input instanceof String)
        {
            return stringInputMutator.modify((String) input, offset);
        }
        else
        {
            throw new UnsupportedOperationException("Class type <" + input.getClass() + "> not supported");
        }
    }
}
