package com3529;

import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.ast.visitor.Visitable;

public class CloneVisitorWithPlaceholder extends CloneVisitor
{
    @Override
    public Visitable visit(final NameExpr n, final Object arg) {
        if (n instanceof PlaceholderExpr) {
            return n.clone();
        }

        return super.visit(n, arg);
    }
}
