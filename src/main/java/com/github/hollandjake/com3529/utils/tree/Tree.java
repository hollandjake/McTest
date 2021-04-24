package com.github.hollandjake.com3529.utils.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The hierarchy of the {@link BranchNode BranchNodes}
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tree implements Cloneable, Iterable<BranchNode>
{
    /**
     * The children of this node
     */
    @ToString.Exclude
    private final List<BranchNode> children = new ArrayList<>();

    public Tree(List<BranchNode> children)
    {
        this.children.addAll(children);
    }

    /**
     * Add a child to this node, setting the child's parent as this
     *
     * @param child the child to add
     */
    public void addChild(BranchNode child)
    {
        children.add(child);
        child.setParentNode(this);
    }

    /**
     * Recursively get a specific {@link ConditionNode} defined by the conditionId
     *
     * @param conditionId the id to search for
     * @return The matched {@link ConditionNode}
     */
    public ConditionNode getConditionNode(int conditionId)
    {
        for (BranchNode child : getChildren())
        {
            ConditionNode found = child.getConditionNode(conditionId);
            if (found != null)
            {
                return found;
            }
        }
        return null;
    }

    /**
     * Recursively get a specific {@link BranchNode} defined by the branchId
     *
     * @param branchId the id to search for
     * @return The matched {@link BranchNode}
     */
    public BranchNode getBranchNode(int branchId)
    {
        for (BranchNode child : getChildren())
        {
            BranchNode found = child.getBranchNode(branchId);
            if (found != null)
            {
                return found;
            }
        }
        return null;
    }

    /**
     * Recursively fetch all the {@link BranchNode BranchNodes} that can be reached from this node
     * @return all the children below this node
     */
    public List<BranchNode> getAllChildren()
    {
        List<BranchNode> allChildren = new ArrayList<>();
        children.forEach(child -> {
            allChildren.add(child);
            allChildren.addAll(child.getAllChildren());
        });
        return allChildren;
    }

    /**
     * Creates a deep copy of the node
     * @return the cloned instance
     */
    @Override
    public Tree clone()
    {
        Tree clone = new Tree();
        children.forEach(child -> clone.addChild(child.clone()));
        return clone;
    }

    /**
     * Produce an iterator to iterate over all the children
     * @return the iterator
     */
    @Override
    public Iterator<BranchNode> iterator()
    {
        return getAllChildren().iterator();
    }

    /**
     * Returns the fitness of this node.
     * This defaults to be 0 for a Tree since this is the root
     *
     * @return 0
     */
    public double getFitness()
    {
        return 0d; // Skip this node
    }

    /**
     * Returns the fitness of this node.
     * This defaults to be 0 for a Tree since this is the root
     *
     * @return 0
     */
    public double getFitness(boolean truthy)
    {
        return 0d; // Skip this node
    }

    /**
     * Join this {@link Tree} to another {@link Tree}
     * @param other the other {@link Tree}
     * @return The joined {@link Tree}
     */
    public Tree join(Tree other)
    {
        Tree cloneNode = new Tree();

        children.forEach(child -> cloneNode.addChild(child.join(other)));

        return cloneNode;
    }
}
