package com.github.hollandjake.com3529.generation;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
            coverageReport = tests.stream().map(test -> {
                test.execute();
                return test.getCoverageReport();
            }).filter(Objects::nonNull)
                                  .collect(Collectors.toList())
                                  .stream()
                                  .reduce(new CoverageReport(), (left, right) -> {
                                      CoverageReport newReport = new CoverageReport();
                                      Map<Integer, BranchCoverage> coverage = newReport.getCoveredBranches();
                                      left.getCoveredBranches().forEach((branchNum, branchCoverage) -> coverage.compute(
                                              branchNum,
                                              (key, val) -> (val == null) ? branchCoverage : val.combine(branchCoverage)
                                      ));
                                      right.getCoveredBranches()
                                           .forEach((branchNum, branchCoverage) -> coverage.compute(
                                                   branchNum,
                                                   (key, val) -> (val == null) ?
                                                           branchCoverage :
                                                           val.combine(branchCoverage)
                                           ));
                                      return newReport;
                                  });
            executed = true;
        }
    }

    public void writeToFile(File file)
    {
        tests.forEach(test -> test.writeToFile(file));
    }
}
