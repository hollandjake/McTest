package com3529;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import lombok.Getter;

@Getter
public class ClassDecomposition
{
    private final Map<SimpleName, Type> variableTypes;
    private final List<BinaryExpr> branches;

    public ClassDecomposition(CompilationUnit cu) {
        this.variableTypes = extractVariableTypes(cu);
        this.branches = extractBranches(cu);
    }

    private Map<SimpleName, Type> extractVariableTypes(CompilationUnit cu) {
        Map<SimpleName, Type> types = new HashMap<>();

        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration n, Void arg) {
                if (n.getParameters() != null)
                {
                    n.getParameters().forEach(parameter -> types.put(parameter.getName(), parameter.getType()));
                }
            }
        }, null);
        return types;
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
