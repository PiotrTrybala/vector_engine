package pl.piotrtrybala.database.query.expression;

import pl.piotrtrybala.database.query.QueryException;

public class ExpressionException extends QueryException {
    public ExpressionException(String message) {
        super(message);
    }
}
