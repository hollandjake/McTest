package com.github.hollandjake.com3529.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.testsuite.TestSuite;
import com.github.hollandjake.com3529.utils.tree.ConditionNode;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
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
    public static void generateJUnitTests(TestSuite testSuite,
            String packageName,
            File outputDirectory)
    {
        //Grab details about class
        Method method = testSuite.getMethod();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String testClassName = className + methodName.substring(0, 1).toUpperCase() + methodName.substring(1) + "Tests";

        log.info("Writing generated tests for \"{}.{}.{}\"", packageName, className, methodName);


        //Create new maven project with JUnit tests
        URI root = outputDirectory.toURI();
        FileUtils.deleteDirectory(outputDirectory);
        outputDirectory.mkdirs();
        String packagePath = packageName.replace(".", "/") + '/';
        URI mainJava = root.resolve("src/main/java/").resolve(packagePath);
        URI testJava = root.resolve("src/test/java/").resolve(packagePath);

        File mainFile = new File(mainJava.resolve(className + ".java"));
        File testFile = new File(testJava.resolve(testClassName + ".java"));
        File pomFile = new File(root.resolve("pom.xml"));

        writeToFile(testFile, testSuite.generateTestSuite().toString());
        copyFile(testSuite.getFileUnderTest(), mainFile);
        writePOMToFile(pomFile, packageName);

        log.info("Tests saved to {}", outputDirectory);
    }

    @SneakyThrows
    public static void generateCoverageReport(TestSuite testSuite, File outputDirectory)
    {
        List<ConditionNode> conditionNodeList = testSuite.getCoverageReport().getConditionNodes();

        Document document = new Document();
        Path filePath = new File(outputDirectory + "/CoverageReport.pdf").toPath().toAbsolutePath();
        PdfWriter.getInstance(document, new FileOutputStream(filePath.toFile()));
        document.open();

        //Fonts
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 25, BaseColor.BLACK);

        //Logo
        URL systemResource = ClassLoader.getSystemResource("logo.png");
        Image img = Image.getInstance(systemResource);
        img.setAlignment(Element.ALIGN_CENTER);
        document.add(img);

        //Title
        Paragraph title = new Paragraph("McTest" + "\n\n");
        title.setFont(font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        //Coverage Report Table
        List<String> failedTexts = new ArrayList<>();
        PdfPTable table = new PdfPTable(new float[] { 15, 20, 30, 15, 15 });
        addTableHeader(table);
        for (ConditionNode conditionNode : conditionNodeList)
        {
            if (conditionNode == null || conditionNode.getConditionCoverage() == null)
            {
                continue;
            }
            String falsy = "-";
            String truthy = "-";
            if (conditionNode.getConditionCoverage().getResult() == null)
            {
                falsy = "Y";
                truthy = "Y";
            }
            else if (!conditionNode.getConditionCoverage().getResult())
            {
                falsy = "Y";
                failedTexts.add("Did not execute true on condition #" + conditionNode.getConditionId());
            }
            else
            {
                truthy = "Y";
                failedTexts.add("Did not execute false on condition #" + conditionNode.getConditionId());
            }
            PdfPCell conditionID = new PdfPCell(new Phrase(String.format("#%s", conditionNode.getConditionId())));
            PdfPCell conditionLocation = new PdfPCell(new Phrase(String.valueOf(
                    conditionNode.getLineRange() != null ? conditionNode.getLineRange().begin : "")));
            PdfPCell conditionExpression = new PdfPCell(new Phrase(String.format("(%s)",
                                                                                 conditionNode.getConditionString()
                                                                                              .replace(" ",
                                                                                                       "\u00A0"))));
            PdfPCell executedTrue = new PdfPCell(new Phrase(truthy));
            PdfPCell executedFalse = new PdfPCell(new Phrase(falsy));
            conditionID.setHorizontalAlignment(Element.ALIGN_CENTER);
            conditionID.setPadding(5);
            conditionLocation.setHorizontalAlignment(Element.ALIGN_CENTER);
            conditionLocation.setPadding(5);
            conditionExpression.setHorizontalAlignment(Element.ALIGN_CENTER);
            conditionExpression.setPadding(5);
            executedTrue.setHorizontalAlignment(Element.ALIGN_CENTER);
            executedTrue.setPadding(5);
            executedFalse.setHorizontalAlignment(Element.ALIGN_CENTER);
            executedFalse.setPadding(5);
            table.addCell(conditionID);
            table.addCell(conditionLocation);
            table.addCell(conditionExpression);
            table.addCell(executedTrue);
            table.addCell(executedFalse);
        }
        table.setWidthPercentage(100);
        document.add(table);

        //Coverage Percentage
        float conditionCoveragePercent = (1 - (float) failedTexts.size() / ((float) conditionNodeList.size() * 2))
                * 100;
        Paragraph percentage = new Paragraph(String.format("Condition Coverage: %.00f%%", conditionCoveragePercent));
        percentage.setFont(font);
        document.add(percentage);

        //Coverage did not execute...
        if (!failedTexts.isEmpty())
        {
            Paragraph para = new Paragraph("The following conditions did not execute");
            para.setFont(font);
            document.add(para);
            com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
            for (String failedText : failedTexts)
            {
                ListItem item = new ListItem(failedText);
                item.setAlignment(Element.ALIGN_JUSTIFIED);
                list.add(item);
            }
            document.add(list);
        }

        document.close();

        log.info("Coverage report saved to {}", filePath);
    }

    static void addTableHeader(PdfPTable table)
    {
        Stream.of("Condition Number", "Location", "Expression", "Executed True", "Executed False")
              .forEach(columnTitle -> {
                  PdfPCell header = new PdfPCell();
                  header.setPadding(5);
                  header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                  header.setHorizontalAlignment(Element.ALIGN_CENTER);
                  header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                  header.setBorderWidth(1);
                  header.setPhrase(new Phrase(columnTitle));
                  table.addCell(header);
              });
    }

    @SneakyThrows
    static void writeToFile(File file, String content)
    {
        new File(file.getParent()).mkdirs();
        try (FileWriter fileWriter = new FileWriter(file))
        {
            fileWriter.write(content);
        }
    }

    @SneakyThrows
    static void copyFile(File source, File dest)
    {
        new File(dest.getParent()).mkdirs();
        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
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
