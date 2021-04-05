package com.github.hollandjake.com3529;

import java.util.Arrays;

import com.github.hollandjake.com3529.generation.Method;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ClassTestGenerator
{
    public static void forClass(String className)
    {
        forClass(ParseConvert.parse(className));
    }

    public static void forClass(ParseConvert mappedClass)
    {
        Class<?> clazz = mappedClass.getClazz();

        Arrays.stream(clazz.getMethods())
              .filter(method -> method.getDeclaringClass() == clazz)
              .forEach(method -> MethodTestGenerator.forMethod(new Method(method, mappedClass.getBranchTree(method))));
    }

    public static void main(String[] args)
    {
        for (int i = 0; i < args.length-1; i++)
        {
            if (args[i].equals("-generate") || args[i].equals("-g")) {
                forClass(args[i+1]);
            }
        }
    }
}
