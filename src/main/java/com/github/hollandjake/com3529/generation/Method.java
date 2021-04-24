package com.github.hollandjake.com3529.generation;

import java.io.File;

import com.github.hollandjake.com3529.utils.tree.Tree;

import lombok.Data;

/**
 * Container for storing information about the method currently under test
 */
@Data
public class Method
{
    private final File fileUnderTest;
    private final String packageUnderTest;
    private final java.lang.reflect.Method executableMethod;
    private final Tree methodTree;
}
