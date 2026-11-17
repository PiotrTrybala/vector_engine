package pl.piotrtrybala.database.command.handlers;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandClassifier;
import pl.piotrtrybala.database.command.CommandException;
import pl.piotrtrybala.database.command.CommandHandler;
import pl.piotrtrybala.database.command.types.UseCommand;

public class UseCommandHandler implements CommandHandler {
    @Override
    public boolean canParse(String query) throws CommandException {
        return CommandClassifier.isUseQuery(query);
    }

    @Override
    public Command parse(String query) throws CommandException {
        return new UseCommand(query);
    }
}
