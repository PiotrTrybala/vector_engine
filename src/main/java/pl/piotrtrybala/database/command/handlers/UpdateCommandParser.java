package pl.piotrtrybala.database.command.handlers;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandClassifier;
import pl.piotrtrybala.database.command.CommandException;
import pl.piotrtrybala.database.command.types.UpdateCommand;

public class UpdateCommandParser implements CommandParser {
    @Override
    public boolean canParse(String query) throws CommandException {
        return CommandClassifier.isUpdateQuery(query);
    }

    @Override
    public Command parse(String query) throws CommandException {
        return new UpdateCommand(query);
    }
}
