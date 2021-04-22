package com.github.hollandjake.com3529;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import com.github.hollandjake.com3529.testsuite.TestSuite;
import com.github.hollandjake.com3529.utils.FileTools;

import org.mockito.MockedStatic;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class ClassTestGeneratorTest
{
    private MockedStatic<FileTools> filesToolsMockedStatic;

    @BeforeMethod
    public void setUp()
    {
        filesToolsMockedStatic = mockStatic(FileTools.class);
        filesToolsMockedStatic.when(() -> FileTools.generateCoverageReport(any(), any()))
                              .thenAnswer(invocation -> null);
        filesToolsMockedStatic.when(() -> FileTools.generateJUnitTests(any(), any(), any()))
                              .thenAnswer(invocation -> null);
    }

    @AfterMethod
    public void tearDown()
    {
        filesToolsMockedStatic.close();
    }

    @Test
    public void testForClassWithClassNameOnly()
    {
        List<TestSuite> generatedTests = ClassTestGenerator.forClass("Triangle.java");
        assertThat(generatedTests, hasSize(1)); //Only has one method so should only have one testsuite
        assertThat(generatedTests.get(0).getTests(), not(empty()));
        filesToolsMockedStatic.verifyNoInteractions();
    }
}