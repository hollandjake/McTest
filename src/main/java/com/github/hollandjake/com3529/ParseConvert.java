package com.github.hollandjake.com3529;

import java.util.concurrent.atomic.AtomicInteger;

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

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParseConvert
{
    @SneakyThrows
    public static Class<?> parse(String classToTest)
    {
        SourceRoot sourceRoot = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ParseConvert.class).resolve("src/main/resources" )
        );
        CompilationUnit cu = sourceRoot.parse("", classToTest + ".java" );

        //Add import to class
        cu.findAll(CompilationUnit.class).forEach(compilationUnit -> {
            ImportDeclaration newImport = StaticJavaParser.parseImport(
                    "import " + CoverageReport.class.getCanonicalName() + ";"
            );
            compilationUnit.addImport(newImport);
        });

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
        });

        //Save n Compile
        return CompilerUtils.CACHED_COMPILER.loadFromJava(classToTest, cu.toString());
    }
}