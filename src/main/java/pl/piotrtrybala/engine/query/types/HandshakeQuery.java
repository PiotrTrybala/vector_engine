package pl.piotrtrybala.engine.query.types;

import pl.piotrtrybala.engine.query.Query;
import pl.piotrtrybala.engine.query.enums.QueryType;

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
