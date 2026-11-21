package pl.piotrtrybala.database.command;

import pl.piotrtrybala.database.command.enums.CommandType;

public interface Command {
    CommandType getQueryType();
}
