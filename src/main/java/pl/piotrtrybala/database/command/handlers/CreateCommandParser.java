package pl.piotrtrybala.database.command.handlers;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandClassifier;
import pl.piotrtrybala.database.command.CommandException;
import pl.piotrtrybala.database.command.types.CreateCommand;

public class CreateCommandParser implements CommandParser {
    @Override
    public boolean canParse(String query) throws CommandException {
        return CommandClassifier.isCreateCollectionQuery(query);
    }

    @Override
    public Command parse(String query) {
        return new CreateCommand(query);
    }
}
