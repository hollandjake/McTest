package com.github.hollandjake.com3529;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ClassTestGenerator
{
    public static List<MethodTestSuite> forClass(String className)
    {
        return forClass(className, "../generatedTests");
    }

    public static List<MethodTestSuite> forClass(String className, String outputDirectory)
    {
        File outputFile = new File(outputDirectory);
        return forClass(ParseConvert.parse(className), outputFile);
    }

    public static List<MethodTestSuite> forClass(ParseConvert mappedClass, File outputDirectory)
    {
        if (!outputDirectory.exists() && !outputDirectory.mkdirs())
        {
            throw new InvalidPathException("Invalid path provided", outputDirectory.toString());
        }
        Class<?> clazz = mappedClass.getClazz();

        return Arrays.stream(clazz.getMethods())
              .parallel()
              .filter(method -> method.getDeclaringClass() == clazz)
              .map(method -> MethodTestGenerator.forMethod(
                      new Method(mappedClass.getFileUnderTest(), method, mappedClass.getBranchTree(method)),
                      mappedClass.getPackageName(),
                      outputDirectory
              ))
              .collect(Collectors.toList());
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
