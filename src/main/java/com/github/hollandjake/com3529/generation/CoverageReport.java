package com.github.hollandjake.com3529.generation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.github.javaparser.ast.expr.Expression;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter(AccessLevel.PACKAGE)
public class CoverageReport
{
    private final Map<Integer, BranchCoverage> coveredBranches;

    public CoverageReport()
    {
        this.coveredBranches = new HashMap<>();
    }

    public static Collector<CoverageReport, CoverageReport, CoverageReport> collect()
    {
        return new Collector<CoverageReport, CoverageReport, CoverageReport>()
        {

            @Override
            public Supplier<CoverageReport> supplier()
            {
                return CoverageReport::new;
            }

            @Override
            public BiConsumer<CoverageReport, CoverageReport> accumulator()
            {
                return (group, report) -> {
                    Map<Integer, BranchCoverage> coverage = group.getCoveredBranches();
                    report.getCoveredBranches().forEach((branchNum, branchCoverage) -> coverage.compute(
                            branchNum,
                            (key, val) -> (val == null) ? branchCoverage : val.combine(branchCoverage)
                    ));
                };
            }

            @Override
            public BinaryOperator<CoverageReport> combiner()
            {
                return (left, right) -> {
                    CoverageReport newReport = new CoverageReport();
                    Map<Integer, BranchCoverage> coverage = newReport.getCoveredBranches();
                    left.getCoveredBranches().forEach((branchNum, branchCoverage) -> coverage.compute(
                            branchNum,
                            (key, val) -> (val == null) ? branchCoverage : val.combine(branchCoverage)
                    ));
                    right.getCoveredBranches().forEach((branchNum, branchCoverage) -> coverage.compute(
                            branchNum,
                            (key, val) -> (val == null) ? branchCoverage : val.combine(branchCoverage)
                    ));
                    return newReport;
                };
            }

            @Override
            public Function<CoverageReport, CoverageReport> finisher()
            {
                return coverageReport -> coverageReport;
            }

            @Override
            public Set<Characteristics> characteristics()
            {
                return Collections.singleton(Characteristics.UNORDERED);
            }
        };
    }

    public boolean cover(int branchNumber, boolean result)
    {
        coveredBranches.compute(
                branchNumber,
                (key, val) -> (val == null) ? new BranchCoverage(result) : val.addResult(result)
        );
        return result;
    }

    public int getTotalBranches()
    {
        return coveredBranches.values().stream()
                              .mapToInt(b -> (b.getCoveredTruthy() ? 1 : 0) + (b.getCoveredFalsy() ? 1 : 0))
                              .sum();
    }
}
