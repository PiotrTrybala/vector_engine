package pl.piotrtrybala.database.command.expression;

import pl.piotrtrybala.database.command.CommandException;

public class ExpressionException extends CommandException {
    public ExpressionException(String message) {
        super(message);
    }
}
