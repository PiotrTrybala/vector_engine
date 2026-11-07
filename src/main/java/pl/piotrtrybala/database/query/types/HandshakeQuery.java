package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Query;
import pl.piotrtrybala.database.query.enums.QueryType;

public class HandshakeQuery implements Query {

    public HandshakeQuery() {}

    @Override
    public String toString() {
        return "HandshakeQuery{}";
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.HANDSHAKE;
    }
}
