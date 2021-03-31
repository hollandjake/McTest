package com.github.hollandjake.com3529.generation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchCoverage
{
    private Boolean coveredTruthy;
    private Boolean coveredFalsy;

    public BranchCoverage(Boolean result) {
        this.coveredTruthy = result;
        this.coveredFalsy = !result;
    }

    public BranchCoverage combine(BranchCoverage branchCoverage)
    {
        return new BranchCoverage(coveredTruthy || branchCoverage.coveredTruthy, coveredFalsy || branchCoverage.coveredFalsy);
    }

    public BranchCoverage addResult(Boolean result)
    {
        return new BranchCoverage(coveredTruthy || result, coveredFalsy || !result);
    }
}
