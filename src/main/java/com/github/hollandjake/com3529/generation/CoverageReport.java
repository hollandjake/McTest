package com.github.hollandjake.com3529.generation;

import java.util.concurrent.atomic.AtomicReference;

import com.github.hollandjake.com3529.utils.tree.Tree;
import com.github.javaparser.ast.expr.Expression;

import lombok.Data;
import lombok.ToString;

@Data
public class CoverageReport
{
    @ToString.Exclude
    private final Tree methodTree;

    public CoverageReport(Tree methodTree)
    {
        this.methodTree = methodTree.clone();
    }

    public boolean cover(int branchNumber, Expression expr)
    {
        BranchCoverage branchCoverage = BranchCoverage.from(branchNumber, expr);
        methodTree.getIfNode(branchNumber).setBranchCoverage(branchCoverage);
        return branchCoverage.getResult();
    }

    public double getFitness()
    {
        AtomicReference<Double> totalFitness = new AtomicReference<>((double) 0);
        methodTree.forEach(node -> totalFitness.updateAndGet(v -> v + node.getFitness()));

        return totalFitness.get();
    }
}
