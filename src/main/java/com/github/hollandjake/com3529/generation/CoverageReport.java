package com.github.hollandjake.com3529.generation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import com.github.hollandjake.com3529.utils.tree.ConditionNode;
import com.github.hollandjake.com3529.utils.tree.Tree;
import com.github.javaparser.ast.expr.BinaryExpr;

import lombok.Data;
import lombok.ToString;

/**
 * Container and operator on which all injected calls pass through.
 * <p>
 * This class stores all the information about an execution.
 */
@Data
public class CoverageReport
{
    /**
     * The execution hierarchy along with all of their fitnesses
     */
    @ToString.Exclude
    private final Tree methodTree;

    public CoverageReport(Tree methodTree)
    {
        this.methodTree = methodTree.clone();
    }

    /**
     * Called by the injected method to track the {@link ConditionCoverage} for each node
     * as well as responsible for returning the correct value as if it was native java running the calculation
     *
     * @param conditionId The id of the condition currently being evaluated
     * @param left        the left operand
     * @param right       the right operand
     * @param operator    the operator
     * @return the result of the expression
     */
    public boolean cover(int conditionId, Object left, Object right, BinaryExpr.Operator operator)
    {
        ConditionCoverage conditionCoverage = ConditionCoverage.from(conditionId, left, right, operator);
        methodTree.getConditionNode(conditionId).setConditionCoverage(conditionCoverage);
        return conditionCoverage.getResult();
    }

    /**
     * Computes the overall fitness for the given execution
     *
     * @return the overall fitness
     */
    public double getFitness()
    {
        AtomicReference<Double> totalFitness = new AtomicReference<>((double) 0);
        methodTree.forEach(node -> totalFitness.updateAndGet(v -> v + node.getRawFitness()));

        return totalFitness.get();
    }

    /**
     * Join a second {@link CoverageReport} onto this instance,
     * integrating all the {@link ConditionCoverage} nodes into the matching nodes
     *
     * @param coverageReport The other report
     * @return The combined {@link CoverageReport}
     */
    public CoverageReport join(CoverageReport coverageReport)
    {
        return new CoverageReport(methodTree.join(coverageReport.getMethodTree()));
    }

    /**
     * Compute a sequence of covered branches
     * <p>
     * This is used during the report generation.
     *
     * @return A {@link Set} of {@link String Strings} representing each condition that has been satisfied
     */
    public Set<String> getBranchesCovered()
    {
        Set<String> branches = new HashSet<>();
        getConditionNodes().forEach(conditionNode -> {
            ConditionCoverage coverage = conditionNode.getConditionCoverage();
            if (coverage != null)
            {
                Boolean result = coverage.getResult();
                if (result != null)
                {
                    if (result)
                    {
                        branches.add(conditionNode.getConditionId() + "t");
                    }
                    else
                    {
                        branches.add(conditionNode.getConditionId() + "f");
                    }
                }
                else
                {
                    branches.add(conditionNode.getConditionId() + "t");
                    branches.add(conditionNode.getConditionId() + "f");
                }
            }
        });

        return branches;
    }

    /**
     * Get all the {@link ConditionNode ConditionNodes} from the {@link CoverageReport}
     *
     * @return All the {@link ConditionNode ConditionNodes}
     */
    public List<ConditionNode> getConditionNodes()
    {
        List<ConditionNode> conditionNodeList = new ArrayList<>();
        methodTree.forEach(node -> conditionNodeList.addAll(node.getConditions()));
        return conditionNodeList;
    }
}
