package com.github.hollandjake.com3529.generation;

import com.github.hollandjake.com3529.utils.tree.Tree;

import lombok.Data;

@Data
public class Method
{
    private final java.lang.reflect.Method executableMethod;
    private final Tree methodTree;
}
