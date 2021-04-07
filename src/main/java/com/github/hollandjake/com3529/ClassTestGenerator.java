package com.github.hollandjake.com3529;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Arrays;

import com.github.hollandjake.com3529.generation.Method;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ClassTestGenerator
{
    public static void forClass(String className)
    {
        forClass(className, "../generatedTests");
    }

    public static void forClass(String className, String outputDirectory)
    {
        File outputFile = new File(outputDirectory);
        forClass(ParseConvert.parse(className), outputFile.toPath());
    }

    public static void forClass(ParseConvert mappedClass, Path outputPath)
    {
        if (!outputPath.toFile().exists())
        {
            throw new InvalidPathException("Invalid path provided", outputPath.toString());
        }
        Class<?> clazz = mappedClass.getClazz();

        Arrays.stream(clazz.getMethods())
              .parallel()
              .filter(method -> method.getDeclaringClass() == clazz)
              .forEach(method -> MethodTestGenerator.forMethod(
                      new Method(mappedClass.getFileUnderTest(), method, mappedClass.getBranchTree(method)),
                      mappedClass.getPackageName(),
                      outputPath
              ));
    }

    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            throw new IllegalArgumentException("No .java file specified");
        }

        String className = null;
        String outputPath = null;
        for (int i = 0; i < args.length - 1; i++)
        {
            if (args[i].equals("-generate") || args[i].equals("-g"))
            {
                className = args[i + 1];
            }
            if (args[i].equals("-output") || args[i].equals("-o"))
            {
                outputPath = args[i + 1];
            }
        }

        //Assume first argument is the classname unless they have specified the command
        if (className == null)
        {
            className = args[0];
        }

        forClass(className, outputPath);
    }
}
