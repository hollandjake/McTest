package com.github.hollandjake.com3529.utils;

import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileTools {

    public static void generateJUnitTests(MethodTestSuite methodTestSuite, Method method)
    {
        //Grab details about class
        String className = method.getExecutableMethod().getDeclaringClass().getSimpleName();
        String methodName = method.getExecutableMethod().getName();
        String testClassName = className+methodName.substring(0, 1).toUpperCase() + methodName.substring(1)+"Tests";
        String packageName = method.getExecutableMethod().getDeclaringClass().getPackage().getName();

        //Create the JUnit tests
        CompilationUnit cu = new CompilationUnit();
        cu.setPackageDeclaration(packageName);
        cu.addImport("org.junit.Test");
        cu.addImport("org.junit.Assert.assertEquals",true,false);
        ClassOrInterfaceDeclaration classDeclaration = cu.addClass(testClassName);
        methodTestSuite.build(classDeclaration, className, methodName);

        //Create new maven project with JUnit tests
        writeToFile(String.format("../generatedTests/src/test/java/%s/%s.java", StringUtils.join(packageName, '/'), testClassName), cu.toString());
        String dest = String.format("../generatedTests/src/main/java/%s/%s.java", StringUtils.join(packageName, '/'), className);
        String source = String.format("src/main/resources/%s.java", className);
        copyFile(source, dest);
        writePOMToFile("../generatedTests/pom.xml", packageName);
    }

    public static void writeToFile(String path, String content)
    {
        try {
            File file = new File(path);
            new File(file.getParent()).mkdirs();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void copyFile(String source, String dest)
    {
        File dirFrom = new File(source);
        File dirTo = new File(dest);
        try {
            new File(dirTo.getParent()).mkdirs();
            dirTo.createNewFile();
            Files.copy(dirFrom.toPath(), dirTo.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void writePOMToFile(String path, String packageName)
    {
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                "    <modelVersion>4.0.0</modelVersion>\n" +
                "\n" +
                "    <artifactId>GENERATED-TESTS</artifactId>\n" +
                "    <groupId>"+packageName+"</groupId>\n" +
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
