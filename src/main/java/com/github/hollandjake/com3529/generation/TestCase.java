package com.github.hollandjake.com3529.generation;

import java.io.File;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import com.github.hollandjake.com3529.utils.tree.Tree;

import org.apache.commons.lang3.ArrayUtils;

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
    @ToString.Exclude
    private final Tree methodTree;
    private final Object[] inputs;

    private boolean executed = false;
    private Object output;
    private CoverageReport coverageReport;

    public void writeToFile(File file) {}

    public boolean execute() {
        if (!executed) {
            try {
                CoverageReport coverage = new CoverageReport(methodTree);
                Object result = method.invoke(method.getDeclaringClass().newInstance(), ArrayUtils.add(inputs, coverage));
                executed = true;
                output = result;
                coverageReport = coverage;
                return true;
            } catch (Exception e) {
                Logger.getLogger(TestCase.class.getName()).warning(e.toString());
            }
        }
        return false;
    }
}
