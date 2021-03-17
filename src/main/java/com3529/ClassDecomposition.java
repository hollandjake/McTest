package com3529;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import lombok.Data;
import lombok.Getter;

@Data
public class ClassDecomposition
{
    private final Map<SimpleName, Type> variableTypes;
    private final List<BinaryExpr> branches;
    private final Type returnType;

    public static ClassDecomposition from(CompilationUnit cu) {
        Map<SimpleName, Type> variableTypes = new HashMap<>();
        List<BinaryExpr> branches = new ArrayList<>();
        final Type[] returnType = new Type[1];

        cu.accept(new VoidVisitorAdapter<Void>()
        {
            @Override
            public void visit(MethodDeclaration n, Void arg)
            {
                returnType[0] = n.getType();
                n.getParameters().forEach(parameter -> variableTypes.put(parameter.getName(), parameter.getType()));
                super.visit(n, arg);
            }

            @Override
            public void visit(BinaryExpr n, Void arg)
            {
                branches.add(n);
            }
        }, null);

        return new ClassDecomposition(variableTypes, branches, returnType[0]);
    }
}
