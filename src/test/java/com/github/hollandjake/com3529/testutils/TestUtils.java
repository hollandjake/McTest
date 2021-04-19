package com.github.hollandjake.com3529.testutils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils
{
    @SneakyThrows
    public static void setFinalStatic(Class<?> clazz, String fieldName, Object newValue) {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }
}
