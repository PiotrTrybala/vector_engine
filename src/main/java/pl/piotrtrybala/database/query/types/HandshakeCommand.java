package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Command;
import pl.piotrtrybala.database.query.enums.QueryType;

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
