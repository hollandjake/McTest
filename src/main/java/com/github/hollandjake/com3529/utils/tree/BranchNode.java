package com.github.hollandjake.com3529.utils.tree;

import java.util.ArrayList;
import java.util.List;

import com.github.hollandjake.com3529.generation.ConditionCoverage;
import com.github.javaparser.ast.expr.BinaryExpr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Representing a branch within the program this can be an
 * if-statement, for-loop, while-loop, do-while-loop, or any other branching operations.
 * <p>
 * Each {@link BranchNode} can contain any number of {@link ConditionNode ConditionNodes}.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BranchNode extends Tree
{
    /**
     * The ordered sequence of conditions that appear inside the branch
     */
    private final List<ConditionNode> conditions = new ArrayList<>();
    /**
     * The ordered sequence of operations that join each {@link #conditions condition} together
     */
    private final List<BinaryExpr.Operator> conditionOperators = new ArrayList<>();
    /**
     * The children of this branch which would be executed as a result of this branch evaluating to true
     */
    @ToString.Exclude
    private final List<BranchNode> thenPath = new ArrayList<>();
    /**
     * The children of this branch which would be executed as a result of this branch evaluating to false
     */
    @ToString.Exclude
    private final List<BranchNode> elsePath = new ArrayList<>();

    private final int ifId;
    @ToString.Exclude
    private Tree parentNode;

    /**
     * Indicates whether this branch was a result of a branch evaluating to true
     */
    @ToString.Exclude
    private boolean isThenChild;

    public BranchNode(Tree parentNode, int ifId)
    {
        this.parentNode = parentNode;
        this.ifId = ifId;
    }

    /**
     * Compute the fitness for the entire {@link BranchNode} in a given direction
     *
     * @param forThenChild indicate whether we want the truthy or falsy fitness
     * @return the fitness of the branch for a given direction
     */
    public double getFitness(boolean forThenChild)
    {
        // If this if node has been reached
        ConditionCoverage totalConditionCoverage = getTotalConditionCoverage();
        if (totalConditionCoverage != null)
        {
            return ConditionCoverage.normalise(
                    forThenChild ? totalConditionCoverage.getTruthDistance() : totalConditionCoverage.getFalseDistance()
            );
        }
        return 1 + parentNode.getFitness(isThenChild);
    }

    /**
     * Compute the overall fitness of both truthy and falsy sides
     *
     * @return the fitness of the branch
     */
    public double getFitness()
    {
        ConditionCoverage totalConditionCoverage = getTotalConditionCoverage();
        if (totalConditionCoverage != null)
        {
            return totalConditionCoverage.getNormalisedFitness();
        }
        return 1 + parentNode.getFitness();
    }

    /**
     * Sums all the fitness's of the nodes within this branch
     *
     * @return the total fitness of all {@link ConditionNode ConditionNodes}
     */
    public double getRawFitness()
    {
        return conditions.stream().mapToDouble(ConditionNode::getFitness).sum();
    }

    /**
     * Create a deep clone of the {@link BranchNode}
     *
     * @return the clone
     */
    @Override
    public BranchNode clone()
    {
        BranchNode clonedNode = new BranchNode(null, ifId);
        conditions.forEach(conditionNode -> clonedNode.addCondition(conditionNode.clone()));
        conditionOperators.forEach(clonedNode::addConditionOperator); // Operator doesnt need cloning since its an enum
        thenPath.forEach(child -> clonedNode.addThenChild(child.clone()));
        elsePath.forEach(child -> clonedNode.addElseChild(child.clone()));
        return clonedNode;
    }

    /**
     * Add an {@link BinaryExpr.Operator} to the list
     *
     * @param operator the operator to add
     */
    public void addConditionOperator(BinaryExpr.Operator operator)
    {
        this.conditionOperators.add(operator);
    }

    /**
     * Add a {@link ConditionNode} to the conditions and assign the condition's parent to be this
     *
     * @param conditionNode the condition to add
     */
    public void addCondition(ConditionNode conditionNode)
    {
        this.conditions.add(conditionNode);
        conditionNode.setParent(this);
    }

    /**
     * Add a {@link BranchNode} child to the truth path of this node
     *
     * @param child the child node to add
     */
    public void addThenChild(BranchNode child)
    {
        this.addChild(child);
        this.thenPath.add(child);
    }

    /**
     * Add a {@link BranchNode} child to the false path of this node
     *
     * @param child the child node to add
     */
    public void addElseChild(BranchNode child)
    {
        this.addChild(child);
        this.elsePath.add(child);
    }

    /**
     * Compute the collective {@link ConditionCoverage} of this branch ensuring that AND "&&" and OR "||" are handled
     *
     * @return the total condition coverage of this branch
     */
    public ConditionCoverage getTotalConditionCoverage()
    {
        ConditionCoverage result = conditions.get(0).getConditionCoverage();

        if (result == null)
        {
            return null;
        }

        for (int i = 0; i < conditions.size() - 1; i++)
        {
            ConditionCoverage right = conditions.get(i + 1).getConditionCoverage();
            BinaryExpr.Operator operator = conditionOperators.get(i);
            switch (operator)
            {
                case AND:
                    result = new ConditionCoverage(
                            result.getConditionId(),
                            null,
                            Math.min(result.getTruthDistance(), right.getTruthDistance()),
                            Math.min(result.getFalseDistance(), right.getFalseDistance())
                    );
                    break;
                case OR:
                    result = new ConditionCoverage(
                            result.getConditionId(),
                            null,
                            result.getTruthDistance() + right.getTruthDistance(),
                            result.getFalseDistance() + right.getFalseDistance()
                    );
                    break;
                default:
                    throw new UnsupportedOperationException(String.format(
                            "Joining operator <%s> not supported",
                            operator
                    ));
            }
        }

        return result;
    }

    /**
     * Recursively get a specific {@link ConditionNode} defined by the conditionId
     *
     * @param conditionId the id to search for
     * @return The matched {@link ConditionNode}
     */
    public ConditionNode getConditionNode(int conditionId)
    {
        return conditions.stream()
                         .filter(conditionNode -> conditionNode.getConditionId() == conditionId)
                         .findAny()
                         .orElseGet(() -> super.getConditionNode(conditionId));
    }

    /**
     * Recursively get a specific {@link BranchNode} defined by the branchId
     *
     * @param branchId the id to search for
     * @return The matched {@link BranchNode}
     */
    public BranchNode getBranchNode(int branchId)
    {
        if (this.ifId == branchId)
        {
            return this;
        }
        return super.getBranchNode(branchId);
    }

    /**
     * Join a {@link BranchNode} instance with a {@link Tree},
     * joining only on the matching {@link BranchNode} inside the tree.
     *
     * @param other The Tree to match against
     * @return The joined {@link BranchNode}
     */
    public BranchNode join(Tree other)
    {
        if (other != null && other.getBranchNode(this.ifId) != null)
        {
            BranchNode cloneNode = new BranchNode(null, ifId);
            this.conditions.forEach(conditionNode -> cloneNode.addCondition(conditionNode.join(other)));
            this.conditionOperators.forEach(cloneNode::addConditionOperator);
            this.thenPath.forEach(child -> cloneNode.addThenChild(child.join(other)));
            this.elsePath.forEach(child -> cloneNode.addElseChild(child.join(other)));
            return cloneNode;
        }
        else
        {
            return this.clone();
        }
    }
}

