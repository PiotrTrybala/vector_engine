package pl.piotrtrybala.database.command.handlers;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandException;

public interface CommandParser {
    boolean canParse(String query) throws CommandException;
    Command parse(String query) throws CommandException;
}
