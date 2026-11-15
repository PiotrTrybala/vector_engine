package pl.piotrtrybala.database.query.expression;

import pl.piotrtrybala.database.query.CommandException;

public class ExpressionException extends CommandException {
    public ExpressionException(String message) {
        super(message);
    }
}
