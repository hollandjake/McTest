package com.github.hollandjake.com3529.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {

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

    public static void writePOMToFile(String path)
    {
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                "    <modelVersion>4.0.0</modelVersion>\n" +
                "\n" +
                "    <artifactId>GENERATED-TESTS</artifactId>\n" +
                "    <groupId>com.github.hollandjake.com3529</groupId>\n" +
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
