package com.github.hollandjake.com3529.generation.solver.mutation;

import java.security.SecureRandom;
import java.util.Random;

import com.typesafe.config.ConfigFactory;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Input mutator factory
 * <p>
 * Responsible for assigning the correct mutator to a given input
 *
 * @param <T> The individual type in which a mutation can be performed on
 */
public abstract class InputMutator<T>
{
    @Getter
    @Accessors(fluent = true)
    private static final Random RANDOM = new SecureRandom();

    /**
     * Distribution of random numbers resulting in a range of -{@link #NUMBER_DISTRIBUTION} to +{@link #NUMBER_DISTRIBUTION}
     */
    @Getter
    @Accessors(fluent = true)
    private static final Number NUMBER_DISTRIBUTION = ConfigFactory.load().getNumber(
            "Genetics.Initial.InputDistribution");

    @Getter
    private static final IntegerInputMutator integerInputMutator = new IntegerInputMutator();
    @Getter
    private static final FloatInputMutator floatInputMutator = new FloatInputMutator();
    @Getter
    private static final DoubleInputMutator doubleInputMutator = new DoubleInputMutator();
    @Getter
    private static final LongInputMutator longInputMutator = new LongInputMutator();
    @Getter
    private static final ByteInputMutator byteInputMutator = new ByteInputMutator();
    @Getter
    private static final ShortInputMutator shortInputMutator = new ShortInputMutator();
    @Getter
    private static final BooleanInputMutator booleanInputMutator = new BooleanInputMutator();
    @Getter
    private static final CharacterInputMutator characterInputMutator = new CharacterInputMutator();
    @Getter
    private static final StringInputMutator stringInputMutator = new StringInputMutator();

    /**
     * Generate a random {@link T}
     *
     * @return The random {@link T}
     */
    public abstract T generate();

    /**
     * Create a new individual which is a mutated version of the {@code value} argument, mutated by {@code offset} amount
     *
     * @param value  The input to mutate
     * @param offset How much to mutate by
     * @return The new mutated individual
     */
    public abstract T modify(T value, double offset);

    /**
     * Factory method to generate a value for a given type
     *
     * @param type the type to generate
     * @return a generated instance of that type
     * @throws UnsupportedOperationException when input type is not supported
     */
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

    /**
     * Factory method to modify an individual by an amount
     *
     * @param input  The input to mutate
     * @param offset How much to mutate by
     * @return The new mutated individual
     * @throws UnsupportedOperationException when input type is not supported
     */
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
