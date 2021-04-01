package com.github.hollandjake.com3529;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ClassTestGenerator
{
    public static void forClass(Class<?> clazz)
    {
        Arrays.stream(clazz.getMethods())
              .filter(method -> Modifier.isPublic(method.getModifiers()))
              .filter(method -> method.getDeclaringClass() != Object.class)
              .forEach(MethodTestGenerator::forMethod);
    }

    public static void forClass(String className)
    {
        Class<?> clazz = ParseConvert.parse(className);

        Arrays.stream(clazz.getMethods())
                .filter(method -> Modifier.isPublic(method.getModifiers()))
                .filter(method -> method.getDeclaringClass() != Object.class)
                .forEach(MethodTestGenerator::forMethod);
    }
}
