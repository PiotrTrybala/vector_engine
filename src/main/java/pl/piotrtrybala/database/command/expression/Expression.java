package pl.piotrtrybala.database.command.expression;

import java.util.List;

public class Expression {

    private Node root;

    public Expression() {}

    public Expression(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public static Expression parse(List<String> tokens) throws ExpressionException {
        if (tokens.isEmpty()) {
            return null;
        }
        Expression expression = new Expression();
        expression.root = Node.parse(tokens);
        return expression;
    }

    public Object getValue(String key) {
        return this.root.getValue(key);
    }
}
