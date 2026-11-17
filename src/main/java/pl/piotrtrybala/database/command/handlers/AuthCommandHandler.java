package pl.piotrtrybala.database.command.handlers;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandClassifier;
import pl.piotrtrybala.database.command.CommandException;
import pl.piotrtrybala.database.command.CommandHandler;
import pl.piotrtrybala.database.command.types.AuthCommand;

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
