package com.github.hollandjake.com3529.generation;

import java.util.logging.Logger;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;

import lombok.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

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
        String statement = String.format("assertEquals(\"%s\", String.valueOf(%s.%s(%s)));",
                                         output.toString(),
                                         className,
                                         methodName,
                                         StringUtils.join(inputs, ','));
        methodDeclaration.setBody(new BlockStmt().addStatement(statement));
    }

    public boolean execute()
    {
        if (!executed)
        {
            try
            {
                CoverageReport coverage = new CoverageReport(method.getMethodTree());
                Object result = method.getExecutableMethod().invoke(
                        method.getExecutableMethod().getDeclaringClass().newInstance(),
                        ArrayUtils.add(inputs, coverage)
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
