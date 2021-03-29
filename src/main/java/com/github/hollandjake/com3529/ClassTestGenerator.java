package com.github.hollandjake.com3529;

import java.lang.reflect.Method;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ClassTestGenerator
{
        public static void forClass(Class<?> clazz) {
                for (Method method : clazz.getMethods())
                {
                        if (method.getDeclaringClass() != Object.class)
                        {
                                MethodTestGenerator.forMethod(method);
                        }
                }
        }
}
