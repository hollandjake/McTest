package com.github.hollandjake.com3529.utils.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
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

    public IfNode getIfNode(int branchId)
    {
        for (IfNode child : getChildren())
        {
            IfNode found = child.getIfNode(branchId);
            if (found != null)
            {
                return found;
            }
        }
        return null;
    }

    public IfNode replaceIfNode(int branchId, IfNode newNode)
    {
        AtomicReference<IfNode> replacement = new AtomicReference<>();
        children.replaceAll(child -> {
            IfNode replaced = child.replaceIfNode(branchId, newNode);
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

    public Tree join(Tree other)
    {
        Tree cloneNode = new Tree();

        children.forEach(child -> cloneNode.addChild(child.join(other)));

        return cloneNode;
    }
}
