package com.github.hollandjake.com3529.generation;

import java.util.Arrays;
import java.util.logging.Logger;

import com.github.hollandjake.com3529.testsuite.Test;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.PrimitiveType;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
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
                                          if (input instanceof Character)
                                          {
                                              return new CharLiteralExpr((Character) input);
                                          }
                                          else if (input instanceof String)
                                          {
                                              return new StringLiteralExpr(((String) input).replace("\"", "\\\""));
                                          }
                                          else if (input instanceof Short)
                                          {
                                              return new CastExpr(PrimitiveType.shortType(), new IntegerLiteralExpr(String.valueOf(input)));
                                          }
                                          else if (input instanceof Byte)
                                          {
                                              return new CastExpr(PrimitiveType.byteType(), new IntegerLiteralExpr(String.valueOf(input)));
                                          }
                                          else
                                          {
                                              return (Expression) StaticJavaParser.parseExpression(String.valueOf(input));
                                          }
                                      })
                                      .toArray(Expression[]::new)
                        )
                )
        );
        methodDeclaration.setBody(new BlockStmt().addStatement(expr));
    }

    public Test finalise()
    {
        return new Test(method.getExecutableMethod(), inputs.clone(), output);
    }

    public boolean execute()
    {
        if (!executed)
        {
            coverageReport = new CoverageReport(method.getMethodTree());
            try
            {
                output = method.getExecutableMethod().invoke(
                        method.getExecutableMethod().getDeclaringClass().newInstance(),
                        ArrayUtils.add(SerializationUtils.clone(inputs), coverageReport)
                );
                executed = true;
                return true;
            }
            catch (Exception e)
            {
                output = e.toString();
                Logger.getLogger(TestCase.class.getName()).warning(e.toString());
            }
        }
        return false;
    }
}
