package com3529;

import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class BranchExtractor extends VoidVisitorAdapter<Void>
{
    @Override
    public void visit(final BlockStmt n, Void arg) {

    }
}
