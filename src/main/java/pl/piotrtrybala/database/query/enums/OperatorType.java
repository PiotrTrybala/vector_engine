package pl.piotrtrybala.database.query.enums;

import pl.piotrtrybala.database.query.expression.ExpressionException;

import java.util.Objects;

public enum OperatorType {
    EQUALS("="),;

    private String value;

    OperatorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "OperatorType{" +
                "value='" + value + '\'' +
                '}';
    }

    public static OperatorType fromString(String value) throws ExpressionException {
        try {
            if (Objects.equals(value, "=")) {
                return OperatorType.EQUALS;
            }
        } catch(Exception e) {
            throw new ExpressionException("invalid operator type: " + value);
        }
        return null;
    }
}
