package com.github.hollandjake.com3529.generation.solver;

import java.util.Random;

import com.typesafe.config.ConfigFactory;

import lombok.experimental.UtilityClass;

@UtilityClass
public class InputGenerator
{
    private static final Random RANDOM = new Random();
    private static final Number NUMBER_DISTRIBUTION = ConfigFactory.load().getNumber(
            "Genetics.Initial.InputDistribution");

    public static Object generate(Class<?> type)
    {
        if (type == Integer.class || type == int.class)
        {
            return Math.abs(RANDOM.nextInt() % NUMBER_DISTRIBUTION.intValue());
        }
        else if (type == Float.class || type == float.class)
        {
            return RANDOM.nextFloat() % NUMBER_DISTRIBUTION.floatValue();
        }
        else if (type == Double.class || type == double.class)
        {
            return RANDOM.nextDouble() % NUMBER_DISTRIBUTION.doubleValue();
        }
        else if (type == Long.class || type == long.class)
        {
            return RANDOM.nextLong() % NUMBER_DISTRIBUTION.longValue();
        }
        else
        {
            throw new UnsupportedOperationException("Class type <" + type + "> not supported");
        }
    }
}
