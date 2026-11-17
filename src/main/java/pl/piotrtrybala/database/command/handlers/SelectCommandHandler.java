package pl.piotrtrybala.database.command.handlers;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandClassifier;
import pl.piotrtrybala.database.command.CommandException;
import pl.piotrtrybala.database.command.CommandHandler;
import pl.piotrtrybala.database.command.types.SelectCommand;

public class SelectCommandHandler implements CommandHandler {
    @Override
    public boolean canParse(String query) throws CommandException {
        return CommandClassifier.isSelectQuery(query);
    }

    @Override
    public Command parse(String query) throws CommandException {
        return new SelectCommand(query);
    }
}
