package pl.piotrtrybala.database.command.expression;

import pl.piotrtrybala.database.command.enums.ConditionType;
import pl.piotrtrybala.database.command.enums.OperatorType;

import java.util.List;

public class Node {

    public static class Value {
        private final String key;
        private final OperatorType operator;
        private final Object value;

        public Value(String key, OperatorType operator, Object value) {
            this.key = key;
            this.operator = operator;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public OperatorType getOperator() {
            return operator;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Value{" +
                    "key='" + key + '\'' +
                    ", operator=" + operator +
                    ", value=" + value +
                    '}';
        }
    }
    
    private Node left;
    private ConditionType condition;
    private Node right;

    private Value value;

    public Node() {}

    public Node(Node left, ConditionType condition, Node right) {
        this.left = left;
        this.condition = condition;
        this.right = right;
        this.value = null;
    }

    public Node(Value value) {
        this.left = null;
        this.condition = ConditionType.NONE;
        this.right = null;
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public ConditionType getCondition() {
        return condition;
    }

    public Node getRight() {
        return right;
    }

    public Value getValue() {
        return value;
    }

    public boolean isLeaf() {
        return this.value != null;
    }

    @Override
    public String toString() {
        return "Node{" +
                "left=" + left +
                ", condition=" + condition +
                ", right=" + right +
                '}';
    }

    public static Node parse(List<String> tokens) throws ExpressionException {

        List<List<String>> segments = ExpressionUtils.splitExpression(tokens);

        Node node = new Node();
        if (segments.size() == 3) {
            node.left = Node.parse(segments.getFirst());
            node.condition = ConditionType.valueOf(segments.get(1).getFirst().toUpperCase());
            node.right = Node.parse(segments.getLast());
        } else if (segments.size() == 2 || segments.size() > 3) {
            throw new ExpressionException("invalid expression: too much segments");
        } else {
            List<String> expression = segments.getFirst();
            if (expression.size() != 3) {
                throw new ExpressionException("invalid expression: " + String.join(" ", expression));
            }

            String key = expression.getFirst().trim();
            OperatorType operator = OperatorType.fromString(expression.get(1));
            Object value = ExpressionUtils.parseValue(expression.getLast());

            node.value = new Value(key, operator, value);
        }
        return node;
    }

    public Object getValue(String key) {

        if (this.isLeaf()) {
            if (this.value.getKey() != null && this.value.getKey().equals(key)) {
                return this.value.getValue();
            }
        }
        if (this.left != null) {
            return this.left.getValue(key);
        }
        if (this.right != null) {
            return this.right.getValue(key);
        }

        return null;
    }
}
