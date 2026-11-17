package pl.piotrtrybala.database.command;

import pl.piotrtrybala.database.command.enums.QueryType;

public interface Command {
    QueryType getQueryType();
}
