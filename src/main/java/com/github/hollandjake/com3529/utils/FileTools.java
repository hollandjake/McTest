package com.github.hollandjake.com3529.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import org.apache.commons.io.FileUtils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class FileTools
{
    @SneakyThrows
    public static void generateJUnitTests(MethodTestSuite methodTestSuite,
            String packageName,
            Path outputPath)
    {
        outputPath = outputPath.toAbsolutePath();
        //Grab details about class
        Method method = methodTestSuite.getMethod().getExecutableMethod();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String testClassName = className + methodName.substring(0, 1).toUpperCase() + methodName.substring(1) + "Tests";

        log.info("Tests generated for \"{}.{}.{}\"", packageName, className, methodName);

        //Create the JUnit tests
        CompilationUnit cu = new CompilationUnit();
        cu.setPackageDeclaration(packageName);
        cu.addImport("org.junit.Test");
        cu.addImport("org.junit.Assert.assertEquals", true, false);
        ClassOrInterfaceDeclaration classDeclaration = cu.addClass(testClassName);
        methodTestSuite.build(classDeclaration, className, methodName);

        //Create new maven project with JUnit tests
        URI root = outputPath.toUri();
        File rootFile = new File(root);
        FileUtils.deleteDirectory(rootFile);
        rootFile.mkdirs();
        String packagePath = packageName.replace(".", "/") + '/';
        URI mainJava = root.resolve("src/main/java/").resolve(packagePath);
        URI testJava = root.resolve("src/test/java/").resolve(packagePath);

        File mainFile = new File(mainJava.resolve(className + ".java"));
        File testFile = new File(testJava.resolve(testClassName + ".java"));
        File pomFile = new File(root.resolve("pom.xml"));

        writeToFile(testFile, cu.toString());
        copyFile(methodTestSuite.getMethod().getFileUnderTest(), mainFile);
        writePOMToFile(pomFile, packageName);

        log.info("Tests saved to {}", rootFile);
    }

    public static void writeToFile(File file, String content)
    {
        try
        {
            new File(file.getParent()).mkdirs();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    public static void copyFile(File source, File dest)
    {
        try
        {
            new File(dest.getParent()).mkdirs();
            dest.createNewFile();
            Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    public static void writePOMToFile(File path, String packageName)
    {
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n"
                +
                "    <modelVersion>4.0.0</modelVersion>\n" +
                "\n" +
                "    <artifactId>GENERATED-TESTS</artifactId>\n" +
                "    <groupId>" + packageName + "</groupId>\n" +
                "    <version>1.0.0</version>\n" +
                "\n" +
                "    <properties>\n" +
                "        <maven.compiler.source>1.8</maven.compiler.source>\n" +
                "        <maven.compiler.target>1.8</maven.compiler.target>\n" +
                "    </properties>\n" +
                "\n" +
                "    <dependencies>\n" +
                "        <dependency>\n" +
                "            <groupId>junit</groupId>\n" +
                "            <artifactId>junit</artifactId>\n" +
                "            <version>4.13.2</version>\n" +
                "            <scope>test</scope>\n" +
                "        </dependency>\n" +
                "    </dependencies>\n" +
                "</project>";
        writeToFile(path, content);
    }
}
