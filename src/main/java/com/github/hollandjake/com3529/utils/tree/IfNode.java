package com.github.hollandjake.com3529.utils.tree;

import java.util.ArrayList;
import java.util.List;

import com.github.hollandjake.com3529.generation.BranchCoverage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IfNode extends Tree
{
    @ToString.Exclude
    private Tree parentNode;
    private BranchCoverage branchCoverage;
    private int distanceFromExecution;

    private final int branchId;
    @ToString.Exclude
    private final List<IfNode> thenPath = new ArrayList<>();
    @ToString.Exclude
    private final List<IfNode> elsePath = new ArrayList<>();

    public IfNode(Tree parentNode, int branchId) {
        this.parentNode = parentNode;
        this.branchId = branchId;
    }

    public void setBranchCoverage(BranchCoverage branchCoverage)
    {
        this.branchCoverage = branchCoverage;
        setDistanceFromExecution(0);
    }

    public void setDistanceFromExecution(int distanceFromExecution)
    {
        this.distanceFromExecution = distanceFromExecution;
        this.getChildren().forEach(child -> child.setDistanceFromExecution(distanceFromExecution + 1));
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

    public IfNode getIfNode(int branchId)
    {
        if (branchId == this.branchId)
        {
            return this;
        }

        return super.getIfNode(branchId);
    }

    public double getFitness()
    {
        if (branchCoverage != null)
        {
            return branchCoverage.getResult() ?
                    branchCoverage.getFalseDistance() :
                    branchCoverage.getTruthDistance();
        }

        return 1 + normalise(parentNode.getFitness());
    }

    public double normalise(double d)
    {
        return d / (d + 1);
    }

    @Override
    public IfNode clone()
    {
        IfNode clonedNode = new IfNode(
                null,
                branchCoverage != null ? branchCoverage.clone() : null,
                distanceFromExecution,
                branchId
        );
        thenPath.forEach(child -> clonedNode.addThenChild(child.clone()));
        elsePath.forEach(child -> clonedNode.addElseChild(child.clone()));
        return clonedNode;
    }
}

