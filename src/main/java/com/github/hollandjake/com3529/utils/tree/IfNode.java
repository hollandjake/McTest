package com.github.hollandjake.com3529.utils.tree;

import java.util.ArrayList;
import java.util.List;

import com.github.hollandjake.com3529.generation.ConditionCoverage;
import com.github.javaparser.ast.expr.BinaryExpr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IfNode extends Tree
{
    private final List<ConditionNode> conditions = new ArrayList<>();
    private final List<BinaryExpr.Operator> conditionOperators = new ArrayList<>();
    @ToString.Exclude
    private final List<IfNode> thenPath = new ArrayList<>();
    @ToString.Exclude
    private final List<IfNode> elsePath = new ArrayList<>();
    private final int ifId;
    @ToString.Exclude
    private Tree parentNode;
    @ToString.Exclude
    private boolean isThenChild;

    public IfNode(Tree parentNode, int ifId)
    {
        this.parentNode = parentNode;
        this.ifId = ifId;
    }

    public double getFitness(boolean forThenChild)
    {
        //If this if node has been reached
        ConditionCoverage totalConditionCoverage = getTotalConditionCoverage();
        if (totalConditionCoverage != null)
        {
            return ConditionCoverage.normalise(
                    forThenChild ? totalConditionCoverage.getTruthDistance() : totalConditionCoverage.getFalseDistance()
            );
        }
        return 1 + parentNode.getFitness(isThenChild);
    }

    public double getFitness()
    {
        ConditionCoverage totalConditionCoverage = getTotalConditionCoverage();
        if (totalConditionCoverage != null)
        {
            return totalConditionCoverage.getNormalisedFitness();
        }
        return 1 + parentNode.getFitness();
    }

    public double getRawFitness()
    {
        return conditions.stream().mapToDouble(ConditionNode::getFitness).sum();
    }

    public ConditionNode getConditionNode(int conditionId)
    {
        return conditions.stream()
                         .filter(conditionNode -> conditionNode.getConditionId() == conditionId)
                         .findAny()
                         .orElseGet(() -> super.getConditionNode(conditionId));
    }

    @Override
    public IfNode clone()
    {
        IfNode clonedNode = new IfNode(null, ifId);
        conditions.forEach(conditionNode -> clonedNode.addCondition(conditionNode.clone()));
        conditionOperators.forEach(clonedNode::addConditionOperator); //Operator doesnt need cloning since its an enum
        thenPath.forEach(child -> clonedNode.addThenChild(child.clone()));
        elsePath.forEach(child -> clonedNode.addElseChild(child.clone()));
        return clonedNode;
    }

    public void addConditionOperator(BinaryExpr.Operator operator)
    {
        this.conditionOperators.add(operator);
    }

    public void addCondition(ConditionNode conditionNode)
    {
        this.conditions.add(conditionNode);
        conditionNode.setParent(this);
    }

    public void addThenChild(IfNode child)
    {
        this.addChild(child);
        this.thenPath.add(child);
    }

    public void addElseChild(IfNode child)
    {
        this.addChild(child);
        this.elsePath.add(child);
    }

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

    public IfNode getIfNode(int ifId)
    {
        if (this.ifId == ifId)
        {
            return this;
        }
        return super.getIfNode(ifId);
    }

    public IfNode join(Tree other)
    {
        if (other != null && other.getIfNode(this.ifId) != null)
        {
            IfNode cloneNode = new IfNode(null, ifId);
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

