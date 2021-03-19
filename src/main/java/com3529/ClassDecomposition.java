package com3529;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import lombok.Data;

@Data
public class ClassDecomposition
{
    private final Map<SimpleName, Type> variableTypes;
    private final List<Expression> branches;
    private final Type returnType;

    public static ClassDecomposition from(CompilationUnit cu)
    {
        Map<SimpleName, Type> variableTypes = new HashMap<>();
        List<List<Expression>> branches = new ArrayList<>();
        final Type[] returnType = new Type[1];
        Stack<Expression> expressionStack = new Stack<>();

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
                expressionStack.push(new EnclosedExpr(n.getCondition()));
                branches.add(new ArrayList<>(expressionStack));

                n.getThenStmt().accept(this, arg);
                expressionStack.pop();
                expressionStack.push(invertExpression(n.getCondition()));
                n.getElseStmt().ifPresent(l -> l.accept(this, arg));
                expressionStack.pop();
            }
        }, null);

        List<Expression> finalbranches = new ArrayList<>();
        for (List<Expression> branchComponents : branches)
        {
            if (branchComponents.size() > 1)
            {
                Expression expression1 = branchComponents.remove(1);
                Expression reduce = branchComponents.stream().reduce(expression1,
                                                                     (a, b) -> new BinaryExpr(a,
                                                                                              b,
                                                                                              BinaryExpr.Operator.AND));
                finalbranches.add(reduce);
            }
            else
            {
                finalbranches.add(branchComponents.get(0));
            }
        }

        return new ClassDecomposition(variableTypes, finalbranches, returnType[0]);
    }

    public static Expression invertExpression(Expression expression)
    {
        return new UnaryExpr(new EnclosedExpr(expression), UnaryExpr.Operator.LOGICAL_COMPLEMENT);
    }
}
