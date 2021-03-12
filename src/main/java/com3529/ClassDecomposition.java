package com3529;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import lombok.Getter;

@Getter
public class ClassDecomposition
{
    private final List<BinaryExpr> branches;

    public ClassDecomposition(CompilationUnit cu) {
        this.branches = extractBranches(cu);
    }

    private List<BinaryExpr> extractBranches(CompilationUnit cu)
    {
        List<BinaryExpr> branches = new ArrayList<>();

        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(BinaryExpr n, Void arg)
            {
                branches.add(n);
            }
        }, null);
        return branches;
    }

}
