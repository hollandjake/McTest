package com3529;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import lombok.Data;
import lombok.Getter;

@Data
public class ClassDecomposition
{
    private final Map<SimpleName, Type> variableTypes;
    private final List<String> branches;
    private final Type returnType;

    public static ClassDecomposition from(CompilationUnit cu) {
        Map<SimpleName, Type> variableTypes = new HashMap<>();
        List<List<String>> branches = new ArrayList<>();
        final Type[] returnType = new Type[1];
        Stack<String> ifStack = new Stack<>();

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
            public void visit(IfStmt n, Void arg)
            {
                ifStack.push("("+n.getCondition().toString()+")");
                branches.add(new ArrayList<>(ifStack));

                n.getThenStmt().accept(this, arg);
                ifStack.pop();
                ifStack.push("!("+n.getCondition().toString()+")");
                n.getElseStmt().ifPresent((l) -> {
                    l.accept(this, arg);
                });
                ifStack.pop();
            }
        }, null);

        List<String> finalbranches = new ArrayList<>();
        for (List<String> branchComponents : branches) {
            finalbranches.add(String.join(" && ", branchComponents));
        }

        return new ClassDecomposition(variableTypes, finalbranches, returnType[0]);
    }
}
