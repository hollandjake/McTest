package com.github.hollandjake.com3529.generation;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Set;

import lombok.Data;

@Data
public class MethodTestSuite
{
    private final Method method;
    private final Set<TestCase> tests;
    private boolean executed = false;
    private CoverageReport coverageReport;

    public void execute() {
        if (!executed)
        {
            coverageReport = tests.stream().map(test -> {
                test.execute();
                return test.getCoverageReport();
            }).collect(CoverageReport.collect());
            executed = true;
        }
    }

    public void writeToFile(File file) {
        tests.forEach(test -> test.writeToFile(file));
    }
}
