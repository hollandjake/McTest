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
            return RANDOM.nextFloat() * NUMBER_DISTRIBUTION.floatValue();
        }
        else if (type == Double.class || type == double.class)
        {
            return RANDOM.nextDouble() * NUMBER_DISTRIBUTION.doubleValue();
        }
        else if (type == Long.class || type == long.class)
        {
            return RANDOM.nextLong() % NUMBER_DISTRIBUTION.longValue();
        }
        else if (type == Boolean.class || type == boolean.class)
        {
            return RANDOM.nextBoolean();
        }
        else if (type == Character.class || type == char.class)
        {
            return (char) (32 + RANDOM.nextInt(128 - 32));
        }
        else if (type == String.class)
        {
            StringBuilder output = new StringBuilder();

            for (int i = 0; i < RANDOM.nextInt(50); i++)
            {
                output.append(generate(Character.class));
            }

            return output.toString();
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
            return (Integer) input + (int) offset;
        }
        else if (input instanceof Float)
        {
            return (Float) input + (float) offset;
        }
        else if (input instanceof Double)
        {
            return (Double) input + offset;
        }
        else if (input instanceof Long)
        {
            return (Long) input + (long) offset;
        }
        else if (input instanceof Boolean)
        {
            return ((Boolean) input ? 1 : 0) + offset % 2 != 0;
        }
        else if (input instanceof Character)
        {
            return (char) (((int)(Character) input + (int) offset) % 65535);
        }
        else if (input instanceof String)
        {
            StringBuilder newString = new StringBuilder();

            for (char c : ((String) input).toCharArray())
            {
                double rand = RANDOM.nextDouble();
                if (rand < 1 / 3d)
                {
                    newString.append(generate(Character.class));
                }
                else if (rand < 2 / 3d)
                {
                    newString.append(c);
                }
                else
                {
                    newString.append(add(c, RANDOM.nextGaussian()));
                }
            }

            for (int i = 0; i < Math.max(0, offset % 10); i++)
            {
                double rand = RANDOM.nextDouble();
                if (rand < 1 / 3d)
                {
                    newString.append(generate(Character.class));
                }
            }

            return newString.toString();
        }
        else
        {
            throw new UnsupportedOperationException("Class type <" + input.getClass() + "> not supported");
        }
    }
}
