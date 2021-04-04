package com.github.hollandjake.com3529.generation;

import java.io.File;
import java.util.Objects;
import java.util.Set;

import lombok.Data;
import lombok.ToString;

@Data
public class MethodTestSuite
{
    @ToString.Exclude
    private final Method method;
    private final Set<TestCase> tests;
    private boolean executed = false;
    private CoverageReport coverageReport;

    public void execute()
    {
        if (!executed)
        {
            coverageReport = new CoverageReport(method.getMethodTree());

            tests.stream()
                 .map(test -> {
                     test.execute();
                     return test.getCoverageReport();
                 })
                 .filter(Objects::nonNull)
                 .forEach(cr -> {
//                     coverageReport.addCoveredBranches(cr.getCoveredBranches())
                 });
            executed = true;
        }
    }

    public void writeToFile(File file)
    {
        tests.forEach(test -> test.writeToFile(file));
    }

    public double getFitness()
    {
        return this.coverageReport.getFitness();
    }
}
