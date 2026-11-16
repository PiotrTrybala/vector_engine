package pl.piotrtrybala.database.query.expression;

import pl.piotrtrybala.database.query.enums.ConditionType;
import pl.piotrtrybala.database.query.enums.OperatorType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
        Expression expression = new Expression();
        expression.root = Node.parse(tokens);
        return expression;
    }

    public Object getValue(String key) {
        return this.root.getValue(key);
    }
}
