package com.github.hollandjake.com3529;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.testsuite.TestSuite;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ClassTestGenerator
{
    /**
     * This method is used for preparing the Class file being tested on with the
     * use of the ParseCovert Class and passes this to the other forClass method
     * which takes in the parsed Class.
     * @param classFilePath This is the path to the Class file being tested on.
     * @return This returns a list of TestSuits for every method in the Class file being tested on.
     */
    public static List<TestSuite> forClass(String classFilePath)
    {
        return forClass(ParseConvert.parse(classFilePath), null);
    }

    /**
     * This method is used for preparing the Class file being tested on with the
     * use of the ParseCovert Class and passes this to the other forClass method
     * which takes in the parsed Class.
     * @param classFilePath This is the path to the Class file being tested on.
     * @param outputDirectory This is the path to the directory where automatic JUnit tests will be generated.
     * @return This returns a list of TestSuits for every method in the Class file being tested on.
     */
    public static List<TestSuite> forClass(String classFilePath, String outputDirectory)
    {
        return forClass(ParseConvert.parse(classFilePath), new File(outputDirectory));
    }

    /**
     * This method will pass every method in the Class file in parallel to the MethodTestGenerator which
     * will generate the tests for the method.
     * @param mappedClass This is the parsed Class.
     * @param outputDirectory This is the path to the directory where automatic JUnit tests will be generated.
     * @return This returns a list of TestSuits for every method in the Class file being tested on.
     */
    public static List<TestSuite> forClass(ParseConvert mappedClass, File outputDirectory)
    {
        if (outputDirectory != null && !outputDirectory.exists() && !outputDirectory.mkdirs())
        {
            throw new InvalidPathException("Invalid path provided", outputDirectory.toString());
        }
        Class<?> clazz = mappedClass.getClazz();

        return Arrays.stream(clazz.getMethods())
                     .parallel()
                     .filter(method -> method.getDeclaringClass() == clazz)
                     .map(method -> MethodTestGenerator.forMethod(
                             new Method(mappedClass.getFileUnderTest(), mappedClass.getPackageName(), method, mappedClass.getBranchTree(method)),
                             mappedClass.getPackageName(),
                             outputDirectory
                     ))
                     .collect(Collectors.toList());
    }

    /**
     * This is main entry point to McTest, it will validate the arguments which are passed to the
     * forClass method to generate the tests.
     * @param args This is an array of arguments for McTest, for example,
     *            -g "Path to class" -o "Path to JUnit output directory".
     */
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

        // Assume first argument is the classname unless they have specified the command
        if (className == null)
        {
            className = args[0];
        }

        forClass(className, outputPath);
    }
}
