package pl.piotrtrybala.engine.query;

public class Expression {
    private final String field;
    private final OperatorType op;
    private final String value;

    public Expression(String field, OperatorType op, String value) {
        this.field = field;
        this.op = op;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public OperatorType getOp() {
        return op;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "field='" + field + '\'' +
                ", op=" + op +
                ", value='" + value + '\'' +
                '}';
    }
}
