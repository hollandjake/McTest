package com.github.hollandjake.com3529.generation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import com.github.hollandjake.com3529.utils.tree.IfNode;
import com.github.hollandjake.com3529.utils.tree.Tree;
import com.github.javaparser.ast.expr.BinaryExpr;

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

    public boolean cover(int conditionId, Object left, Object right, BinaryExpr.Operator operator)
    {
        ConditionCoverage conditionCoverage = ConditionCoverage.from(conditionId, left, right, operator);
        methodTree.getConditionNode(conditionId).setConditionCoverage(conditionCoverage);
        return conditionCoverage.getResult();
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
        methodTree.forEach(node -> node.getConditions().forEach(conditionNode -> {
            ConditionCoverage coverage = conditionNode.getConditionCoverage();
            if (coverage != null) {
                if (coverage.getResult()) {
                    branches.add(conditionNode.getConditionId() + "t");
                } else if (!coverage.getResult()) {
                    branches.add(conditionNode.getConditionId() + "f");
                } else {
                    branches.add(conditionNode.getConditionId() + "t");
                    branches.add(conditionNode.getConditionId() + "f");
                }
            }
        }));

        return branches;
    }
}
