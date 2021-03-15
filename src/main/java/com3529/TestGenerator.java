package com3529;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

public class TestGenerator
{
    private TestGenerator() {}

    public static void of(String classToTest) {
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(TestGenerator.class).resolve("src/main/resources"));

        // Our sample is in the root of this directory, so no package name.
        CompilationUnit cu = sourceRoot.parse("", classToTest);

        ClassDecomposition decomposition = new ClassDecomposition(cu);

        decomposition.getBranches().forEach(branch -> {
            new TruthTable(decomposition.getVariableTypes(), branch);
        });

        System.out.println(decomposition.getBranches());
    }
}
