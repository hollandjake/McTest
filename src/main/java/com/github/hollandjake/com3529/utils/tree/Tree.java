package com.github.hollandjake.com3529.utils.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.jetbrains.annotations.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tree implements Cloneable, Iterable<IfNode>
{
    @ToString.Exclude
    private final List<IfNode> children = new ArrayList<>();

    public Tree(List<IfNode> children)
    {
        this.children.addAll(children);
    }

    public void addChild(IfNode child)
    {
        children.add(child);
        child.setParentNode(this);
    }

    public ConditionNode getConditionNode(int conditionId)
    {
        for (IfNode child : getChildren())
        {
            ConditionNode found = child.getConditionNode(conditionId);
            if (found != null)
            {
                return found;
            }
        }
        return null;
    }

    public IfNode getIfNode(int ifId)
    {
        for (IfNode child : getChildren())
        {
            IfNode found = child.getIfNode(ifId);
            if (found != null)
            {
                return found;
            }
        }
        return null;
    }

    public IfNode replaceConditionNode(int conditionId, IfNode newNode)
    {
        AtomicReference<IfNode> replacement = new AtomicReference<>();
        children.replaceAll(child -> {
            IfNode replaced = child.replaceConditionNode(conditionId, newNode);
            if (replaced != null)
            {
                replacement.set(replaced);
                return replaced;
            }
            else
            {
                return child;
            }
        });

        return replacement.get();
    }

    public List<IfNode> getAllChildren()
    {
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
        Tree clone = new Tree();
        children.forEach(child -> clone.addChild(child.clone()));
        return clone;
    }

    @NotNull
    @Override
    public Iterator<IfNode> iterator()
    {
        return getAllChildren().iterator();
    }

    public double getFitness()
    {
        return 0d; //Skip this node
    }

    public double getFitness(boolean truthy)
    {
        return 0d; //Skip this node
    }

    public Tree join(Tree other)
    {
        Tree cloneNode = new Tree();

        children.forEach(child -> cloneNode.addChild(child.join(other)));

        return cloneNode;
    }
}
