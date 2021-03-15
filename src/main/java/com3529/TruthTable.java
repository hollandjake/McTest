package com3529;

import java.util.Map;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.SimpleName;

import com.github.javaparser.ast.type.Type;

import lombok.Data;

@Data
public class TruthTable
{
    private final Map<SimpleName, Type> variables;
    private final BinaryExpr expression;

    public void compute() {

    }
}
