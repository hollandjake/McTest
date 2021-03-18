package com3529;

import com.github.javaparser.ast.Generated;
import com.github.javaparser.ast.expr.NameExpr;

public class PlaceholderExpr extends NameExpr
{
    public PlaceholderExpr()
    {
        super("Placeholder");
    }

    @Override
    @Generated("com.github.javaparser.generator.core.node.CloneGenerator")
    public PlaceholderExpr clone() {
        return new PlaceholderExpr();
    }
}
