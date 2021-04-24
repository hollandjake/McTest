package com.github.hollandjake.com3529.utils.tree;

import com.github.hollandjake.com3529.generation.ConditionCoverage;
import com.github.javaparser.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Represents a conditional expression inside the {@link Tree} hierarchy
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ConditionNode implements Cloneable
{
    @ToString.Exclude
    private BranchNode parent;
    private final int conditionId;
    private ConditionCoverage conditionCoverage;
    private final String conditionString;
    private final Range lineRange;

    /**
     * Calculate the fitness of the node
     * If the node itself doesn't have a {@link ConditionCoverage} i.e. its not been reached.
     * Then the nodes fitness is recursively defined as the 1 + the parents fitness
     *
     * @return The fitness of the node
     */
    public double getFitness()
    {
        if (conditionCoverage != null)
        {
            return conditionCoverage.getNormalisedFitness();
        }
        return 1 + parent.getFitness();
    }

    /**
     * Creates a deep clone of the node
     *
     * @return The cloned instance
     */
    public ConditionNode clone()
    {
        return new ConditionNode(
                null,
                conditionId,
                conditionCoverage != null ? conditionCoverage.clone() : null,
                conditionString,
                lineRange
        );
    }

    /**
     * Join a {@link ConditionNode} instance with a {@link Tree},
     * joining only on the matching {@link ConditionNode} inside the tree.
     *
     * @param other The Tree to match against
     * @return The joined {@link ConditionNode}
     */
    public ConditionNode join(Tree other)
    {
        ConditionNode otherNode = other.getConditionNode(conditionId);
        if (otherNode != null)
        {
            ConditionNode cloneNode = new ConditionNode(this.conditionId, conditionString, lineRange);
            cloneNode.setConditionCoverage(ConditionCoverage.join(conditionCoverage, otherNode.getConditionCoverage()));
            cloneNode.setParent(parent);

            return cloneNode;
        }
        else
        {
            ConditionNode clone = this.clone();
            clone.setParent(parent);
            return clone;
        }
    }
}
