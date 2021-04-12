package com.github.hollandjake.com3529.utils.tree;

import com.github.hollandjake.com3529.generation.ConditionCoverage;
import com.github.javaparser.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ConditionNode implements Cloneable
{
    @ToString.Exclude
    private IfNode parent;
    private final int conditionId;
    private ConditionCoverage conditionCoverage;
    private final String conditionString;
    private final Range lineRange;

    public void setConditionCoverage(ConditionCoverage conditionCoverage)
    {
        this.conditionCoverage = conditionCoverage;
    }

    public double getFitness()
    {
        if (conditionCoverage != null) {
            return conditionCoverage.getNormalisedFitness();
        }
        return 1 + parent.getFitness();
    }

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

    public ConditionNode join(Tree other)
    {
        ConditionNode otherNode = other.getConditionNode(conditionId);
        if (otherNode != null)
        {
            ConditionNode cloneNode = new ConditionNode(this.conditionId, conditionString, lineRange);
            cloneNode.setConditionCoverage(ConditionCoverage.join(conditionCoverage, otherNode.getConditionCoverage()));
            cloneNode.setParent(parent);

            return cloneNode;
        } else {
            return this.clone();
        }
    }
}
