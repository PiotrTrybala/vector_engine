package pl.piotrtrybala.database.query.handlers;

import pl.piotrtrybala.database.query.Query;
import pl.piotrtrybala.database.query.QueryClassifier;
import pl.piotrtrybala.database.query.QueryException;
import pl.piotrtrybala.database.query.QueryHandler;
import pl.piotrtrybala.database.query.types.HandshakeQuery;

public class HandshakeQueryHandler implements QueryHandler {
    @Override
    public boolean canParse(String query) throws QueryException {
        return QueryClassifier.isHandshakeQuery(query);
    }

    @Override
    public Query parse(String query) {
        return new HandshakeQuery();
    }
}
