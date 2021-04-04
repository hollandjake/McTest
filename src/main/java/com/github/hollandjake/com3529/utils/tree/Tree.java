package com.github.hollandjake.com3529.utils.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import com.github.hollandjake.com3529.generation.BranchCoverage;

import org.jetbrains.annotations.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public class Tree implements Cloneable, Iterable<IfNode> {
    @ToString.Exclude
    private final List<IfNode> children = new ArrayList<>();

    public Tree(List<IfNode> children) {
        this.children.addAll(children);
    }

    public void addChild(IfNode child) {
        children.add(child);
    }

    public IfNode getIfNode(int branchId)
    {
        for (IfNode child: getChildren())
        {
            IfNode found = child.getIfNode(branchId);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public List<IfNode> getAllChildren() {
        List<IfNode> allChildren = new ArrayList<>();
        children.forEach(child -> {
            allChildren.add(child);
            allChildren.addAll(child.getAllChildren());
        });
        return allChildren;
    }

    @Override
    public Tree clone()
    {
        return new Tree(new ArrayList<>(children));
    }

    public void search(Consumer<Tree> action) {
        action.accept(this);
        children.forEach(child -> child.search(action));
    }

    @NotNull
    @Override
    public Iterator<IfNode> iterator()
    {
        return getAllChildren().iterator();
    }

    public double getFitness()
    {
        return 0; //Skip this node
    }

    public int distanceFromExecution() {
        return 0; //This node will always be executed;
    }
}
