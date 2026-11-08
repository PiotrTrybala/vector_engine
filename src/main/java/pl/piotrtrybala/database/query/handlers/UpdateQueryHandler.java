package pl.piotrtrybala.database.query.handlers;

import pl.piotrtrybala.database.query.Query;
import pl.piotrtrybala.database.query.QueryClassifier;
import pl.piotrtrybala.database.query.QueryException;
import pl.piotrtrybala.database.query.QueryHandler;
import pl.piotrtrybala.database.query.types.UpdateQuery;

public class UpdateQueryHandler implements QueryHandler {
    @Override
    public boolean canParse(String query) throws QueryException {
        return QueryClassifier.isUpdateQuery(query);
    }

    @Override
    public Query parse(String query) throws QueryException {
        return new UpdateQuery(query);
    }
}
