package com.github.hollandjake.com3529;

import java.util.Arrays;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ClassTestGenerator
{
    public static void forClass(Class<?> clazz)
    {
        forClass(ParseConvert.parse(clazz));
    }

    public static void forClass(String className)
    {
        forClass(ParseConvert.parse(className));
    }

    public static void forClass(ParseConvert mappedClass)
    {
        Class<?> clazz = mappedClass.getClazz();

        Arrays.stream(clazz.getMethods())
              .filter(method -> method.getDeclaringClass() == clazz)
              .forEach(MethodTestGenerator::forMethod);
    }
}
