package com.github.hollandjake.com3529.utils.tree;

import java.util.ArrayList;
import java.util.List;

import com.github.hollandjake.com3529.generation.BranchCoverage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IfNode extends Tree
{
    @ToString.Exclude
    private final Tree parentNode;
    private BranchCoverage branchCoverage;
    private int distanceFromExecution;

    private final int branchId;
    @ToString.Exclude
    private final List<IfNode> thenPath = new ArrayList<>();
    @ToString.Exclude
    private final List<IfNode> elsePath = new ArrayList<>();

    public IfNode(Tree parentNode, int branchId)
    {
        super();
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
}
