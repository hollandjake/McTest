package com.github.hollandjake.com3529.utils;

import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.utils.tree.ConditionNode;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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

    @SneakyThrows
    public static void generateCoverageReport(MethodTestSuite methodTestSuite, Path outputPath)
    {
        List<ConditionNode> conditionNodeList = methodTestSuite.getCoverageReport().getConditionNodes();

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(outputPath.toString()+"/CoverageReport.pdf"));
        document.open();

        //Fonts
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 25, BaseColor.BLACK);

        //Logo
        Path path = Paths.get(ClassLoader.getSystemResource("logo.png").toURI());
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.setAlignment(Element.ALIGN_CENTER);
        document.add(img);

        //Title
        Paragraph title = new Paragraph("McTest"+"\n\n");
        title.setFont(font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        //Coverage Report Table
        List<String> failedTexts = new ArrayList<>();
        PdfPTable table = new PdfPTable(3);
        addTableHeader(table);
        for (ConditionNode conditionNode : conditionNodeList) {
            String falsy = "X";
            String truthy = "X";
            if (conditionNode.getConditionCoverage().getResult() == null) {
                falsy = "Y";
                truthy = "Y";
            } else if (!conditionNode.getConditionCoverage().getResult()) {
                falsy = "Y";
                failedTexts.add("Did not execute true on condition "+conditionNode.getConditionId());
            } else {
                truthy = "Y";
                failedTexts.add("Did not execute false on condition "+conditionNode.getConditionId());
            }
            PdfPCell conditionID = new PdfPCell(new Phrase(String.format("%d - Line %d (%s)",conditionNode.getConditionId(), conditionNode.getLineNumber(), conditionNode.getConditionString())));
            PdfPCell executedTrue = new PdfPCell(new Phrase(truthy));
            PdfPCell executedFalse = new PdfPCell(new Phrase(falsy));
            conditionID.setHorizontalAlignment(Element.ALIGN_LEFT);
            executedTrue.setHorizontalAlignment(Element.ALIGN_CENTER);
            executedFalse.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(conditionID);
            table.addCell(executedTrue);
            table.addCell(executedFalse);
        }
        document.add(table);

        //Coverage Percentage
        float conditionCoveragePercent = (1 - (float) failedTexts.size() / ((float) conditionNodeList.size()*2)) * 100;
        Paragraph percentage = new Paragraph(String.format("Condition Coverage: %.00f%%", conditionCoveragePercent));
        percentage.setFont(font);
        document.add(percentage);

        //Coverage did not execute...
        if (failedTexts.size() > 0) {
            Paragraph para = new Paragraph("The following conditions did not execute");
            para.setFont(font);
            document.add(para);
            com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
            for (String failedText : failedTexts) {
                ListItem item = new ListItem(failedText);
                item.setAlignment(Element.ALIGN_JUSTIFIED);
                list.add(item);
            }
            document.add(list);
        }

        document.close();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Condition", "Executed True", "Executed False")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
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
