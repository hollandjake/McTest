package com.github.hollandjake.com3529.generation;

import java.util.Arrays;
import java.util.logging.Logger;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter(AccessLevel.PRIVATE)

@RequiredArgsConstructor
public class TestCase
{
    @ToString.Exclude
    private final Method method;
    private final Object[] inputs;

    @Setter
    private boolean executed = false;
    private Object output;
    private CoverageReport coverageReport;

    public void build(MethodDeclaration methodDeclaration, String className, String methodName)
    {
        MethodCallExpr expr = new MethodCallExpr(
                "assertEquals",
                new StringLiteralExpr(output.toString()),
                new MethodCallExpr(
                        "String.valueOf",
                        new MethodCallExpr(
                                String.format("%s.%s", className, methodName),
                                Arrays.stream(inputs)
                                      .map(input -> {
                                          if (input instanceof Character) {
                                              return new CharLiteralExpr((Character) input);
                                          } else if (input instanceof String) {
                                              return new StringLiteralExpr(((String) input).replace("\"", "\\\""));
                                          } else {
                                              return (Expression) StaticJavaParser.parseExpression(String.valueOf(input));
                                          }
                                      })
                                      .toArray(Expression[]::new)
                        )
                )
        );
        methodDeclaration.setBody(new BlockStmt().addStatement(expr));
    }

    public boolean execute()
    {
        if (!executed)
        {
            try
            {
                CoverageReport coverage = new CoverageReport(method.getMethodTree().clone());
                Object result = method.getExecutableMethod().invoke(
                        method.getExecutableMethod().getDeclaringClass().newInstance(),
                        ArrayUtils.add(SerializationUtils.clone(inputs), coverage)
                );
                executed = true;
                output = result;
                coverageReport = coverage;
                return true;
            }
            catch (Exception e)
            {
                Logger.getLogger(TestCase.class.getName()).warning(e.toString());
            }
        }
        return false;
    }
}
