package pl.piotrtrybala.engine.query.expression;

import pl.piotrtrybala.engine.query.enums.ConditionType;
import pl.piotrtrybala.engine.query.enums.OperatorType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Expression {

    private Expression left;
    private ConditionType cond;
    private Expression right;

    private String key;
    private OperatorType op;
    private Object value;

    public static Expression parse(List<String> parts) throws Exception {

        List<List<String>> split = Expression.split(parts);

        Expression expression = new Expression();

        if (split.size() == 3) { // left part, condition, // right part
            expression.left = parse(split.getFirst());
            expression.cond = ConditionType.valueOf(split.get(1).getFirst());
            expression.right = parse(split.getLast());
        } else if (split.size() == 1) {

            List<String> expr = split.getFirst();
            if (expr.size() != 3) {
                throw new Exception("invalid expression: " + expr);
            }

            expression.key = expr.getFirst().trim();
            expression.op = OperatorType.fromString(expr.get(1));
            expression.value = Expression.parseValue(expr.getLast());
        } else {
            throw new Exception("invalid expression: not enough tokens");
        }

        return expression;
    }

    public static boolean eval(HashMap<String, Object> kv) {
        return true;
    }

    private static Object parseValue(String value) {
        
        if (value == null) return null;

        if ((value.startsWith("\"") && value.endsWith("\"")) || (value.startsWith("'") && value.endsWith("'"))) {
            return value.substring(1, value.length() - 1);
        }

        if ("true".equalsIgnoreCase(value)) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase(value)) {
            return Boolean.FALSE;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ignored) {
        }

        return value;
    }

    private static List<List<String>> split(List<String> source) {
        List<List<String>> output = new ArrayList<>();
        List<String> sub = new ArrayList<>();
        for (String part : source) {
            if (part.toUpperCase().equals(ConditionType.OR.getKeyword()) || part.toUpperCase().equals(ConditionType.AND.getKeyword())) {
                output.add(new ArrayList<>(sub));
                output.add(new ArrayList<>(Collections.singleton(part.toUpperCase())));
                sub.clear();
            } else {
                sub.add(part);
            }
        }

        output.add(sub);

        return output;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "left=" + left +
                ", cond=" + cond +
                ", right=" + right +
                ", key='" + key + '\'' +
                ", op=" + op +
                ", value=" + value +
                '}';
    }
}
