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

/**
 * Container for an individual gene of a {@link MethodTestSuite}
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TestCase
{
    /**
     * The method under test
     */
    @ToString.Exclude
    private final Method method;
    /**
     * The sequence of input values to the method
     */
    private final Object[] inputs;

    @Setter
    private boolean executed = false;
    /**
     * The result as returned from the execution of the {@link #method} with the {@link #inputs} as arguments
     */
    private Object output;
    private CoverageReport coverageReport;

    /**
     * Produce the finalised version of a {@link TestCase} in which none of the parameters can be modified
     * @return Unmodifiable {@link Test}
     */
    public Test finalise()
    {
        return new Test(method.getExecutableMethod(), inputs.clone(), output);
    }

    /**
     * If the {@link TestCase} has yet to be run and evaluated this will
     * execute the method storing all the coverage information
     * inside {@link #coverageReport}
     */
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
