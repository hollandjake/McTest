package com.github.hollandjake.com3529.testsuite;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.VoidType;

import lombok.Data;

@Data
public class Test
{
    private final Method method;
    private final Object[] inputs;
    private final Object output;

    public MethodDeclaration generateTestCase()
    {
        return new MethodDeclaration(
                new NodeList<>(Modifier.publicModifier()),
                new NodeList<>(StaticJavaParser.parseAnnotation("@Test")),
                new NodeList<>(),
                new VoidType(),
                new SimpleName("test"),
                new NodeList<>(),
                new NodeList<>(),
                new BlockStmt().addStatement(new MethodCallExpr(
                        "assertEquals",
                        new StringLiteralExpr(output.toString()),
                        new MethodCallExpr(
                                "String.valueOf",
                                new MethodCallExpr(
                                        String.format("%s.%s",
                                                      method.getDeclaringClass().getSimpleName(),
                                                      method.getName()),
                                        Arrays.stream(inputs)
                                              .map(input -> {
                                                  if (input instanceof Character)
                                                  {
                                                      return new CharLiteralExpr((Character) input);
                                                  }
                                                  else if (input instanceof String)
                                                  {
                                                      return new StringLiteralExpr(((String) input).replace("\"",
                                                                                                            "\\\""));
                                                  }
                                                  else if (input instanceof Short)
                                                  {
                                                      return new CastExpr(
                                                              PrimitiveType.shortType(),
                                                              new IntegerLiteralExpr(String.valueOf(input))
                                                      );
                                                  }
                                                  else if (input instanceof Byte)
                                                  {
                                                      return new CastExpr(
                                                              PrimitiveType.byteType(),
                                                              new IntegerLiteralExpr(String.valueOf(input))
                                                      );
                                                  }
                                                  else
                                                  {
                                                      return (Expression) StaticJavaParser.parseExpression(
                                                              String.valueOf(input)
                                                      );
                                                  }
                                              })
                                              .toArray(Expression[]::new)
                                )
                        )
                ))
        );
    }
}
