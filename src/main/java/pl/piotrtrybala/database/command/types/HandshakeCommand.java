package pl.piotrtrybala.database.command.types;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.enums.QueryType;

public class HandshakeCommand implements Command {


    public HandshakeCommand() {}

    @Override
    public String toString() {
        return "HandshakeQuery{}";
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.HANDSHAKE;
    }
}
