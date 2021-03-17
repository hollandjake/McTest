package com3529;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.javaparser.ast.expr.BinaryExpr;

import org.mariuszgromada.math.mxparser.Expression;

import lombok.Data;

@Data
public class TruthTable
{
    private static final Pattern GET_OPERATORS = Pattern.compile("(\\|\\|)|(&&)");

    private final List<ConditionPredicate> conditionPredicates;

    private final int numberOfConditions;

    public static TruthTable from(BinaryExpr expression)
    {

        String expressionString = expression.toString();

        final Matcher matcher = TruthTable.GET_OPERATORS.matcher(expressionString);

        List<String> operators = new ArrayList<>();
        while (matcher.find())
        {
            operators.add(matcher.group(0));
        }

        int n = operators.size() + 1;

        List<ConditionPredicate> conditionPredicates = new ArrayList<>();

        String binaryFormatPattern = "%" + n + "s";

        //Generate all permutations of conditions and evaluate the expressions
        for (int i = 0; i < 1 << n; i++)
        {
            StringBuilder testString = new StringBuilder();
            List<Boolean> conditions = new ArrayList<>();
            char[] binaryCharSequence = String.format(binaryFormatPattern, Integer.toBinaryString(i)).toCharArray();
            int j = 0;

            for (char c : binaryCharSequence)
            {
                boolean isTruthy = c == '1';
                conditions.add(isTruthy);
                testString.append(isTruthy ? '1' : '0');

                if (j < n - 1)
                {
                    testString.append(operators.get(j));
                    j++;
                }
            }

            boolean predicate = new Expression(testString.toString()).calculate() == 1;

            conditionPredicates.add(new ConditionPredicate(conditions, predicate));
        }

        return new TruthTable(conditionPredicates, n);
    }

    public TruthTable toMCDC() {
        Set<ConditionPredicate> newConditionPredicates = new HashSet<>();

        for (int i = 0; i < numberOfConditions; i++) {
            ConditionPredicate truthy = null;
            ConditionPredicate falsey = null;
            for (ConditionPredicate conditionPredicate : this.conditionPredicates) {
                List<Boolean> conditions = conditionPredicate.getConditions();
                if (conditions.get(i)) {
                    if (truthy == null) {
                        if (conditionPredicate.getPredicate()) {
                            truthy = conditionPredicate;
                        }
                    }
                } else {
                    if (falsey == null) {
                        if (!conditionPredicate.getPredicate()) {
                            falsey = conditionPredicate;
                        }
                    }
                }
            }
            newConditionPredicates.add(truthy);
            newConditionPredicates.add(falsey);
        }

        List<ConditionPredicate> newListConditionPredicates = new ArrayList<>(newConditionPredicates);

        return new TruthTable(newListConditionPredicates,this.numberOfConditions);
    }
}
