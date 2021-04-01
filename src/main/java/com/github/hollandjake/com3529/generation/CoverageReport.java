package com.github.hollandjake.com3529.generation;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public String toString()
    {
        return "CoverageReport(branchCoverage="+getTotalBranches()+",coveredBranches=" + coveredBranches + ")";
    }
}
