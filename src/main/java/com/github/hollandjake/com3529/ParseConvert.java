package com.github.hollandjake.com3529;

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

public class ParseConvert {
    private static int branch = 0;
    private Class<?> clazz;

    public ParseConvert(String classToTest) {
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(ParseConvert.class).resolve("src/main/resources"));
        CompilationUnit cu = sourceRoot.parse("", classToTest+".java");

        //Add import to class
        cu.findAll(CompilationUnit.class).forEach((compilationUnit) -> {
            ImportDeclaration newImport = StaticJavaParser.parseImport("import com.github.hollandjake.com3529.generation.CoverageReport;");
            compilationUnit.addImport(newImport);
        });

        cu.findAll(MethodDeclaration.class).forEach((declaration) -> {
            //Add parameter to method
            Parameter newParameter = StaticJavaParser.parseParameter("CoverageReport coverage");
            declaration.addParameter(newParameter);

            //Replace every if statement
            branch = 0;
            declaration.findAll(Statement.class).forEach((statement) -> {
                if (statement.isIfStmt()) {
                    IfStmt ifStmt = (IfStmt) statement;
                    String expression = ifStmt.getCondition().toString();
                    Expression newCondition = StaticJavaParser.parseExpression("coverage.cover("+branch+", "+expression+")");
                    ifStmt.setCondition(newCondition);
                    branch++;
                }
            });
        });

        //Save n Compile
        try {
            clazz = CompilerUtils.CACHED_COMPILER.loadFromJava(classToTest, cu.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Class<?> getClazz(){
        return clazz;
    }
}