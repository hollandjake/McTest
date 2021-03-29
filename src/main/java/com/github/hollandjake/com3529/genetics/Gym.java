package com.github.hollandjake.com3529.genetics;

import java.util.HashMap;
import java.util.Map;

import com.github.javaparser.ast.expr.Expression;

import lombok.Data;

@Data
public class Gym
{
    private final Map<Integer, Fitness> fitnesses = new HashMap<>();

    public boolean workout(Integer index, Expression expression)
    {
        Fitness fitness = Fitness.forExpression(expression);
        fitnesses.put(index, fitness);
        return fitness.getResult();
    }
}
