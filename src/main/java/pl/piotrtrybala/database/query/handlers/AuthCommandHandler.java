package pl.piotrtrybala.database.query.handlers;

import pl.piotrtrybala.database.query.Command;
import pl.piotrtrybala.database.query.CommandClassifier;
import pl.piotrtrybala.database.query.CommandException;
import pl.piotrtrybala.database.query.CommandHandler;
import pl.piotrtrybala.database.query.types.AuthCommand;

public class AuthCommandHandler implements CommandHandler {

    @Override
    public boolean canParse(String query) throws CommandException {
        return CommandClassifier.isAuthQuery(query);
    }

    @Override
    public Command parse(String query) {
        return new AuthCommand(query);
    }
}
