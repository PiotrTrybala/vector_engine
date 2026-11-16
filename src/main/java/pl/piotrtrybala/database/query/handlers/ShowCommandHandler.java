package pl.piotrtrybala.database.query.handlers;

import pl.piotrtrybala.database.query.Command;
import pl.piotrtrybala.database.query.CommandClassifier;
import pl.piotrtrybala.database.query.CommandException;
import pl.piotrtrybala.database.query.CommandHandler;
import pl.piotrtrybala.database.query.types.ShowCommand;

public class ShowCommandHandler implements CommandHandler {
    @Override
    public boolean canParse(String query) throws CommandException {
        return CommandClassifier.isShowQuery(query);
    }

    @Override
    public Command parse(String query) throws CommandException {
        return new ShowCommand(query);
    }
}
