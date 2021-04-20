package com.github.hollandjake.com3529.generation.solver.mutation;

import java.util.List;
import java.util.stream.Collectors;

public class StringInputMutator extends InputMutator<String>
{
    @Override
    public String generate()
    {
        CharacterInputMutator charBuilder = new CharacterInputMutator();
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < InputMutator.RANDOM().nextInt(50); i++)
        {
            output.append(charBuilder.generate());
        }

        return output.toString();
    }

    @Override
    public String modify(String value, double offset)
    {
        int remainingOffset = (int) Math.abs(offset);

        if (remainingOffset <= 0)
        {
            return value;
        }

        List<Character> newString = value.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        CharacterInputMutator charBuilder = InputMutator.getCharacterInputMutator();

        for (int i = 0; i < remainingOffset; i++)
        {
            if (newString.isEmpty())
            {
                newString.add(charBuilder.generate());
            }
            else
            {
                double rand = InputMutator.RANDOM().nextDouble();
                if (rand < 2 / 5d)
                {
                    //Do character incrementing
                    int charIndex = InputMutator.RANDOM().nextInt(newString.size());
                    newString.set(charIndex,
                                  charBuilder.modify(newString.get(charIndex),
                                                     InputMutator.RANDOM().nextGaussian() * newString.size()));
                }
                else if (rand < 3 / 5d)
                {
                    //inject
                    Character newChar = charBuilder.generate();
                    int charIndex = InputMutator.RANDOM().nextInt(newString.size());
                    newString.add(charIndex, newChar);
                }
                else if (rand < 4 / 5d)
                {
                    //append
                    newString.add(charBuilder.generate());
                }
                else
                {
                    //Remove character
                    int charIndex = InputMutator.RANDOM().nextInt(newString.size());
                    newString.remove(charIndex);
                }
            }
        }

        StringBuilder response = new StringBuilder();
        newString.forEach(response::append);
        return response.toString();
    }
}
