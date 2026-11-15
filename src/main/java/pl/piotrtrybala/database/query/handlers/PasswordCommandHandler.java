package pl.piotrtrybala.database.query.handlers;

import pl.piotrtrybala.database.query.Command;
import pl.piotrtrybala.database.query.CommandException;
import pl.piotrtrybala.database.query.CommandHandler;

public class PasswordCommandHandler implements CommandHandler {
    @Override
    public boolean canParse(String query) throws CommandException {
        return false;
    }

    @Override
    public Command parse(String query) throws CommandException {
        return null;
    }
}
