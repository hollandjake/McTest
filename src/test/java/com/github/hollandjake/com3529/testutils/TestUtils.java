package com.github.hollandjake.com3529.testutils;

import java.util.Random;

import com.github.hollandjake.com3529.generation.solver.mutation.InputMutator;

import org.mockito.MockedStatic;

import lombok.experimental.UtilityClass;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

@UtilityClass
public class TestUtils
{
    public static MockedStatic<InputMutator> mockInputMutator()
    {
        MockedStatic<InputMutator> inputMutator = mockStatic(InputMutator.class);
        Random random = new Random(1);
        inputMutator.when(InputMutator::RANDOM).thenReturn(random);
        inputMutator.when(InputMutator::NUMBER_DISTRIBUTION).thenReturn(100);
        inputMutator.when(() -> InputMutator.add(any(), anyDouble())).thenCallRealMethod();
        inputMutator.when(() -> InputMutator.generate(any())).thenCallRealMethod();
        inputMutator.when(InputMutator::getCharacterInputMutator).thenCallRealMethod();

        return inputMutator;
    }
}
