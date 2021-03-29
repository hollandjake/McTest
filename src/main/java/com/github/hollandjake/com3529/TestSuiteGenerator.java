package com.github.hollandjake.com3529;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestSuiteGenerator
{
    public static void forPackage(String packageName)
    {
        List<ClassLoader> classLoadersList = new LinkedList<>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setScanners(
                                new SubTypesScanner(false /* don't exclude Object.class */),
                                new ResourcesScanner()
                        )
                        .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                        .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName)))
        );

        reflections.getSubTypesOf(Object.class).forEach(ClassTestGenerator::forClass);
    }
}
