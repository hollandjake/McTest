package com.github.hollandjake.com3529.generation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import com.github.hollandjake.com3529.utils.tree.IfNode;
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

    public CoverageReport join(CoverageReport coverageReport)
    {
        return new CoverageReport(methodTree.join(coverageReport.getMethodTree()));
    }

    public Set<String> getBranchesCovered()
    {
        Set<String> branches = new HashSet<>();
        Iterator<IfNode> iterator = methodTree.iterator();
        while (iterator.hasNext())
        {
            IfNode node = iterator.next();
            if (node.getBranchCoverage() != null)
            {
                if (node.getBranchCoverage().getResult())
                {
                    branches.add(node.getBranchId() + "t");
                }
                else
                {
                    branches.add(node.getBranchId() + "f");
                }
            }
        }

        return branches;
    }
}
