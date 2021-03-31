package com.github.hollandjake.com3529.generation.solver;

import java.util.Random;

import lombok.experimental.UtilityClass;

@UtilityClass
public class InputGenerator
{
    private static final Random RANDOM = new Random();

    public static Object generate(Class<?> type)
    {
        if (type == Integer.class || type == int.class)
        {
            return RANDOM.nextInt() % 100;
        }
        else if (type == Float.class || type == float.class)
        {
            return RANDOM.nextFloat() % 100;
        }
        else if (type == Double.class || type == double.class)
        {
            return RANDOM.nextDouble() % 100;
        }
        else if (type == Long.class || type == long.class)
        {
            return RANDOM.nextLong() % 100;
        }
        else
        {
            throw new UnsupportedOperationException("Class type <" + type + "> not supported");
        }
    }
}
