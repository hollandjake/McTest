package com.github.hollandjake.com3529.generation;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

import com.github.hollandjake.com3529.utils.tree.Tree;

import lombok.Data;
import lombok.ToString;

@Data
public class MethodTestSuite
{
    @ToString.Exclude
    private final Method method;
    @ToString.Exclude
    private final Tree methodTree;

    private final Set<TestCase> tests;
    private boolean executed = false;
    private CoverageReport coverageReport;

    public void execute()
    {
        if (!executed)
        {
            coverageReport = new CoverageReport(methodTree);

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
