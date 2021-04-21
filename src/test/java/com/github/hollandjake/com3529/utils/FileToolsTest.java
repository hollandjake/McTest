package com.github.hollandjake.com3529.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;

import com.github.hollandjake.com3529.generation.ConditionCoverage;
import com.github.hollandjake.com3529.generation.CoverageReport;
import com.github.hollandjake.com3529.generation.Method;
import com.github.hollandjake.com3529.generation.MethodTestSuite;
import com.github.hollandjake.com3529.utils.tree.ConditionNode;
import com.github.javaparser.Position;
import com.github.javaparser.Range;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.commons.io.FileUtils;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.stubbing.Answer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FileToolsTest
{
    private MockedStatic<FileUtils> filesUtilsMockedStatic;
    private MockedStatic<FileTools> filesToolsMockedStatic;

    @BeforeMethod
    public void setUp()
    {
        filesUtilsMockedStatic = mockStatic(FileUtils.class);
        filesToolsMockedStatic = mockStatic(FileTools.class);

        filesToolsMockedStatic.when(() -> FileTools.generateJUnitTests(any(), anyString(), any())).thenCallRealMethod();
        filesToolsMockedStatic.when(() -> FileTools.generateCoverageReport(any(), any())).thenCallRealMethod();
    }

    @AfterMethod
    public void tearDown()
    {
        filesToolsMockedStatic.close();
        filesUtilsMockedStatic.close();
    }

    @Test
    public void testGenerateJUnitTests()
    {
        MethodTestSuite mockTestSuite = mock(MethodTestSuite.class);
        Method mockMethod = mock(Method.class);
        java.lang.reflect.Method mockMethodExecutable = mock(java.lang.reflect.Method.class);
        File mockOutput = mock(File.class);
        URI mockUri = mock(URI.class);

        filesToolsMockedStatic.when(() -> FileTools.writeToFile(any(), anyString()))
                              .thenAnswer((Answer<Void>) invocation -> null);
        filesToolsMockedStatic.when(() -> FileTools.copyFile(any(), any()))
                              .thenAnswer((Answer<Void>) invocation -> null);
        filesToolsMockedStatic.when(() -> FileTools.writePOMToFile(any(), anyString()))
                              .thenAnswer((Answer<Void>) invocation -> null);

        when(mockTestSuite.getMethod()).thenReturn(mockMethod);
        when(mockMethod.getExecutableMethod()).thenReturn(mockMethodExecutable);

        when(mockMethodExecutable.getDeclaringClass()).thenReturn((Class) String.class);
        when(mockMethodExecutable.getName()).thenReturn("testMethod");

        when(mockOutput.toURI()).thenReturn(mockUri);
        when(mockUri.resolve(anyString())).thenReturn(mockUri);

        when(mockUri.isAbsolute()).thenReturn(true);
        when(mockUri.isOpaque()).thenReturn(false);
        when(mockUri.getScheme()).thenReturn("file");
        when(mockUri.getAuthority()).thenReturn(null);
        when(mockUri.getFragment()).thenReturn(null);
        when(mockUri.getQuery()).thenReturn(null);
        when(mockUri.getPath()).thenReturn("mockPath");

        try (MockedConstruction<File> fileMockedConstruction = mockConstruction(File.class, (mock, context) -> {
            when(mock.mkdirs()).thenReturn(false);
            when(mock.exists()).thenReturn(false);
        }))
        {
            FileTools.generateJUnitTests(mockTestSuite, "com.github.hollandjake.com3529.test", mockOutput);
        }

        filesToolsMockedStatic.verify(times(1), () -> FileTools.writeToFile(any(), anyString()));
        filesToolsMockedStatic.verify(times(1), () -> FileTools.copyFile(any(), any()));
        filesToolsMockedStatic.verify(times(1), () -> FileTools.writePOMToFile(any(), anyString()));
    }

    @Test(expectedExceptions = IOException.class)
    public void testGenerateJUnitTestsThrowsException()
    {
        MethodTestSuite mockTestSuite = mock(MethodTestSuite.class);
        Method mockMethod = mock(Method.class);
        java.lang.reflect.Method mockMethodExecutable = mock(java.lang.reflect.Method.class);
        File mockOutput = mock(File.class);

        filesUtilsMockedStatic.when(() -> FileUtils.deleteDirectory(any())).thenThrow(IOException.class);

        when(mockTestSuite.getMethod()).thenReturn(mockMethod);
        when(mockMethod.getExecutableMethod()).thenReturn(mockMethodExecutable);

        when(mockMethodExecutable.getDeclaringClass()).thenReturn((Class) String.class);
        when(mockMethodExecutable.getName()).thenReturn("testMethod");

        try (MockedConstruction<File> fileMockedConstruction = mockConstruction(File.class, (mock, context) -> {
            when(mock.mkdirs()).thenReturn(false);
            when(mock.exists()).thenReturn(false);
        }))
        {
            FileTools.generateJUnitTests(mockTestSuite, "com.github.hollandjake.com3529.test", mockOutput);
        }
    }

    @Test
    public void testGenerateCoverageReport()
    {
        MethodTestSuite mockTestSuite = mock(MethodTestSuite.class);
        CoverageReport mockCoverage = mock(CoverageReport.class);
        ConditionNode mockCondition1 = mock(ConditionNode.class);
        ConditionNode mockCondition2 = mock(ConditionNode.class);
        ConditionNode mockCondition3 = mock(ConditionNode.class);
        ConditionNode mockCondition4 = mock(ConditionNode.class);
        File mockOutput = mock(File.class);

        filesToolsMockedStatic.when(() -> FileTools.writeToFile(any(), anyString())).thenAnswer(invocation -> null);
        filesToolsMockedStatic.when(() -> FileTools.copyFile(any(), any())).thenAnswer(invocation -> null);
        filesToolsMockedStatic.when(() -> FileTools.writePOMToFile(any(), anyString())).thenAnswer(invocation -> null);

        when(mockTestSuite.getCoverageReport()).thenReturn(mockCoverage);
        when(mockCoverage.getConditionNodes()).thenReturn(Arrays.asList(mockCondition1,
                                                                        mockCondition2,
                                                                        mockCondition3,
                                                                        mockCondition4,
                                                                        null));

        when(mockCondition1.getConditionCoverage()).thenReturn(new ConditionCoverage(0, null, 0d, 0d));
        when(mockCondition1.getConditionId()).thenReturn(0);
        when(mockCondition1.getLineRange()).thenReturn(null);
        when(mockCondition1.getConditionString()).thenReturn("condition1");

        when(mockCondition2.getConditionCoverage()).thenReturn(new ConditionCoverage(1, false, 1d, 0d));
        when(mockCondition2.getConditionId()).thenReturn(0);
        when(mockCondition2.getLineRange()).thenReturn(null);
        when(mockCondition2.getConditionString()).thenReturn("condition2");

        when(mockCondition3.getConditionCoverage()).thenReturn(new ConditionCoverage(2, true, 0d, 1d));
        when(mockCondition3.getConditionId()).thenReturn(0);
        Range mockRange = new Range(mock(Position.class), mock(Position.class));
        when(mockCondition3.getLineRange()).thenReturn(mockRange);
        when(mockCondition3.getConditionString()).thenReturn("condition3");

        when(mockCondition4.getConditionCoverage()).thenReturn(null);

        try (
                MockedConstruction<FileOutputStream> mockOutputStream = mockConstruction(FileOutputStream.class);
                MockedStatic<Image> imageMockedStatic = mockStatic(Image.class);
                MockedStatic<PdfWriter> pdfWriterMockedStatic = mockStatic(PdfWriter.class);
        )
        {
            Image mockImage = mock(Image.class);

            imageMockedStatic.when(() -> Image.getInstance(any(URL.class))).thenReturn(mockImage);
            PdfDocument mockDocument = mock(PdfDocument.class);
            pdfWriterMockedStatic.when(() -> PdfWriter.getInstance(any(), any())).thenAnswer(invocation -> {
                invocation.getArgument(0, Document.class).addDocListener(mockDocument);
                return null;
            });

            when(mockImage.isContent()).thenReturn(true);
            when(mockImage.type()).thenReturn(Element.JPEG);

            FileTools.generateCoverageReport(mockTestSuite, mockOutput);

            verify(mockDocument, times(1)).close();
        }
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void testGenerateCoverageReportThrowsException()
    {
        MethodTestSuite mockTestSuite = mock(MethodTestSuite.class);
        CoverageReport mockCoverage = mock(CoverageReport.class);
        File mockOutput = mock(File.class);

        when(mockTestSuite.getCoverageReport()).thenReturn(mockCoverage);
        when(mockCoverage.getConditionNodes()).thenReturn(Collections.emptyList());

        FileTools.generateCoverageReport(mockTestSuite, mockOutput);
    }

    @Test
    public void testWriteToFileThrowsException()
    {
        PrintStream originalOut = System.err;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outContent));
        filesToolsMockedStatic.when(() -> FileTools.writeToFile(any(), anyString())).thenCallRealMethod();
        File mockFile = mock(File.class);
        when(mockFile.getParent()).thenReturn("");
        try (
                MockedConstruction<FileWriter> writerMockedConstruction = mockConstruction(
                        FileWriter.class,
                        (mock, context) -> doThrow(IOException.class).when(mock).close()
                );
                MockedConstruction<File> fileMockedConstruction = mockConstruction(File.class)
        )
        {
            FileTools.writeToFile(mockFile, "");
        }
        assertThat(outContent.toString(), containsString("java.io.IOException"));
        System.setErr(originalOut);
    }

    @Test
    public void testCopyFileHandlesException()
    {
        PrintStream originalOut = System.err;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outContent));
        filesToolsMockedStatic.when(() -> FileTools.copyFile(any(), any())).thenCallRealMethod();
        File mockFile = mock(File.class);
        when(mockFile.getParent()).thenReturn("");
        Path mockPath = mock(Path.class);
        when(mockFile.toPath()).thenReturn(mockPath);
        MockedStatic<Files> filesMockedStatic = mockStatic(Files.class);
        filesMockedStatic.when(() -> Files.copy(any(Path.class), any(Path.class), any(CopyOption.class)))
                         .thenThrow(IOException.class);
        try (MockedConstruction<File> fileMockedConstruction = mockConstruction(File.class))
        {
            FileTools.copyFile(mockFile, mockFile);
        }
        filesMockedStatic.close();
        assertThat(outContent.toString(), containsString("java.io.IOException"));
        System.setErr(originalOut);
    }
}