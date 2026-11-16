package pl.piotrtrybala.database.query.handlers;

import pl.piotrtrybala.database.query.Command;
import pl.piotrtrybala.database.query.CommandClassifier;
import pl.piotrtrybala.database.query.CommandException;
import pl.piotrtrybala.database.query.CommandHandler;
import pl.piotrtrybala.database.query.types.SelectCommand;

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
