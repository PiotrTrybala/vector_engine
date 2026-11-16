package pl.piotrtrybala.database.query.expression;

import pl.piotrtrybala.database.query.enums.ConditionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpressionUtils {

    public static Object parseValue(String raw) {
        if (raw == null) return null;

        // string
        if ((raw.startsWith("\"") && raw.endsWith("\"")) || (raw.startsWith("'") && raw.endsWith("'"))) {
            return raw.substring(1, raw.length() - 1);
        }

        // boolean
        if ("true".equalsIgnoreCase(raw)) {
            return Boolean.TRUE;
        }

        if ("false".equalsIgnoreCase(raw)) {
            return Boolean.FALSE;
        }

        // integer
        try {
            return Integer.parseInt(raw);
        } catch(NumberFormatException ignored) {}

        // double
        try {
            return Double.parseDouble(raw);
        } catch(NumberFormatException ignored) {}

        return null;
    }

    public static List<List<String>> splitExpression(List<String> tokens) {
        List<List<String>> segments = new ArrayList<>();
        List<String> tmp = new ArrayList<>();
        for (String token : tokens) {
            if (ConditionType.is(token, ConditionType.OR) || ConditionType.is(token, ConditionType.AND)) {
                segments.add(new ArrayList<>(tmp));
                segments.add(new ArrayList<>(Collections.singleton(token.toUpperCase())));
                tmp.clear();
            } else {
                tmp.add(token);
            }
        }

        segments.add(tmp);
        return segments;
    }

}
