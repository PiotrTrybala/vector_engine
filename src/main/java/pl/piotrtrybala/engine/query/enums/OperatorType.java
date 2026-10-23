package pl.piotrtrybala.engine.query.enums;

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

    public static OperatorType fromString(String value) throws Exception {
        try {
            if (Objects.equals(value, "=")) {
                return OperatorType.EQUALS;
            }
        } catch(Exception e) {
            throw new Exception("invalid operator type: " + value);
        }
        return null;
    }
}
