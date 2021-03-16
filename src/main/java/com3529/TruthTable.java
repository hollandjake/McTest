package com3529;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.SimpleName;

import com.github.javaparser.ast.type.Type;

import lombok.Data;
import org.mariuszgromada.math.mxparser.Expression;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Data
public class TruthTable
{
    private final List<ConditionPredicate> conditionPredicates;

    public static TruthTable from(BinaryExpr expression) throws ScriptException {
        // a && b
        // a && b || c
        // Operators = [OR, AND]
        // Conditions = [FALSE, FALSE, FALSE]
        // new conditions = [false, false]
        // new operators = [AND]
        // new condtions = [false]
        // new operators = []
        // 1. Loop Operators for AND index i
        // 2. Conditions[i] Operator[i] Conditions[i+1]
        // = FALSE && FALSE
        // Predicate = FALSE

        final String regex = "(\\|\\|)|(&&)";
        final String string = expression.toString();

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(string);

        List<BinaryExpr.Operator> operators = new ArrayList<>();
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                operators.add(BinaryExpr.Operator.OR);
            }  else {
                operators.add(BinaryExpr.Operator.AND);
            }
        }

        int n = operators.size() + 1;
        double m = Math.pow(2,n);
        List<ConditionPredicate> conditionPredicates = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            List<Boolean> conditions = new ArrayList<>();
            String binary = String.format("%"+n+"s", Integer.toBinaryString(i)).replace(' ', '0');
            for (char ch: binary.toCharArray()) {
                if (ch == '0') {
                    conditions.add(false);
                } else {
                    conditions.add(true);
                }
            }

            String runthis = "";
            for (int z = 0; z < conditions.size(); z++) {
                runthis += conditions.get(z) ? "1" : "0";
                if (z != conditions.size() - 1) {
                    runthis += operators.get(z).asString();
                }
            }

            Expression e = new Expression(runthis);
            double v = e.calculate();
            boolean predicate = v == 1;

            conditionPredicates.add(new ConditionPredicate(conditions, predicate));
        }

        //MCDC
        //

        return new TruthTable(conditionPredicates);
    }


}
