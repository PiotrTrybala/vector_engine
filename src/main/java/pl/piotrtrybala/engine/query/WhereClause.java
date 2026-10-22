package pl.piotrtrybala.engine.query;

public class WhereClause {

    private final Expression left;
    private final ConditionType type;
    private final Expression right;

    public WhereClause(Expression left, ConditionType type, Expression right) {
        this.left = left;
        this.type = type;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public ConditionType getType() {
        return type;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "WhereClause{" +
                "left=" + left +
                ", type=" + type +
                ", right=" + right +
                '}';
    }
}
