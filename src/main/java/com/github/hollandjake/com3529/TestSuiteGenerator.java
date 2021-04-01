package com.github.hollandjake.com3529;

import java.lang.reflect.Modifier;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestSuiteGenerator
{
    public static void forPackage(String packageName)
    {
        new Reflections(packageName, new SubTypesScanner(false), new ResourcesScanner())
                .getSubTypesOf(Object.class)
                .stream()
                .filter(clazz-> Modifier.isPublic(clazz.getModifiers()))
                .forEach(ClassTestGenerator::forClass);
    }
}
