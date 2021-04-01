package com.github.hollandjake.com3529;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.github.hollandjake.com3529.generation.CoverageReport;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

import net.openhft.compiler.CompilerUtils;

import lombok.Data;
import lombok.SneakyThrows;

@Data
public class ParseConvert
{
    private final Map<String, Integer> numBranches;
    private final Class<?> clazz;

    @SneakyThrows
    public static ParseConvert parse(String classToTest)
    {
        SourceRoot sourceRoot = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ParseConvert.class).resolve("src/main/resources" )
        );
        CompilationUnit cu = sourceRoot.parse("", classToTest + ".java" );

        AtomicReference<String> packageName = new AtomicReference<>("" );
        cu.getPackageDeclaration().ifPresent(packageDeclaration -> packageName.set(packageDeclaration.getNameAsString()+"."));

        //Add import to class
        cu.findAll(CompilationUnit.class).forEach(compilationUnit -> {
            ImportDeclaration newImport = StaticJavaParser.parseImport(
                    "import " + CoverageReport.class.getCanonicalName() + ";"
            );
            compilationUnit.addImport(newImport);
        });

        Map<String, Integer> numBranches = new HashMap<>();
        cu.findAll(MethodDeclaration.class).forEach(declaration -> {
            //Add parameter to method
            Parameter newParameter = StaticJavaParser.parseParameter(
                    CoverageReport.class.getSimpleName() + " coverage"
            );
            declaration.addParameter(newParameter);

            //Replace every if statement
            AtomicInteger branch = new AtomicInteger(0);
            declaration.findAll(Statement.class).forEach(statement -> {
                if (statement.isIfStmt())
                {
                    IfStmt ifStmt = (IfStmt) statement;
                    String expression = ifStmt.getCondition().toString();
                    Expression newCondition = StaticJavaParser.parseExpression(
                            "coverage.cover(" + branch.getAndIncrement() + ", " + expression + ")"
                    );
                    ifStmt.setCondition(newCondition);
                }
            });
            numBranches.put(declaration.getNameAsString(), branch.get());
        });

        //Save n Compile
        return new ParseConvert(numBranches, CompilerUtils.CACHED_COMPILER.loadFromJava(packageName.get()+classToTest, cu.toString()));
    }
}