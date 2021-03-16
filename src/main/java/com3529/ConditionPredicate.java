package com3529;

import lombok.Data;

import java.util.List;

@Data
public class ConditionPredicate {

    private final List<Boolean> conditions;
    private final Boolean predicate;

}
