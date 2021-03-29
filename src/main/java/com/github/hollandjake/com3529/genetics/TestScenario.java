package com.github.hollandjake.com3529.genetics;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestScenario
{
    private final List<Object> inputs;
    private final Object output;
    private final Gym gym;

    @SneakyThrows
    public static TestScenario forMethod(Method method, List<Object> inputs) {
        Gym gym = new Gym();

        List<Object> args = new ArrayList<>(inputs);
        args.add(gym);
        Object result = method.invoke(null, args.toArray());
        return new TestScenario(inputs, result, gym);
    }
}
