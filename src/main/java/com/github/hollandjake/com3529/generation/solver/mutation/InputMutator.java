package com.github.hollandjake.com3529.generation.solver.mutation;

import java.security.SecureRandom;
import java.util.Random;

import com.typesafe.config.ConfigFactory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;

public abstract class InputMutator<T>
{
    @Accessors(fluent = true)
    @Getter(value = AccessLevel.PUBLIC)
    private static final Random RANDOM = new SecureRandom();
    @Accessors(fluent = true)
    @Getter(value = AccessLevel.PUBLIC)
    private static final Number NUMBER_DISTRIBUTION = ConfigFactory.load().getNumber("Genetics.Initial.InputDistribution");

    private static final IntegerInputMutator integerInputMutator = new IntegerInputMutator();
    private static final FloatInputMutator floatInputMutator = new FloatInputMutator();
    private static final DoubleInputMutator doubleInputMutator = new DoubleInputMutator();
    private static final LongInputMutator longInputMutator = new LongInputMutator();
    private static final ByteInputMutator byteInputMutator = new ByteInputMutator();
    private static final ShortInputMutator shortInputMutator = new ShortInputMutator();
    private static final BooleanInputMutator booleanInputMutator = new BooleanInputMutator();
    @Getter(value = AccessLevel.PUBLIC) private static final CharacterInputMutator characterInputMutator = new CharacterInputMutator();
    private static final StringInputMutator stringInputMutator = new StringInputMutator();

    public abstract T generate();

    public abstract T modify(T value, double offset);

    public static Object generate(Class<?> type)
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
        else if (type == Byte.class || type == byte.class)
        {
            return byteInputMutator.generate();
        }
        else if (type == Short.class || type == short.class)
        {
            return shortInputMutator.generate();
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

    public static Object add(Object input, double offset)
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
        else if (input instanceof Byte)
        {
            return byteInputMutator.modify((byte) input, offset);
        }
        else if (input instanceof Short)
        {
            return shortInputMutator.modify((Short) input, offset);
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
