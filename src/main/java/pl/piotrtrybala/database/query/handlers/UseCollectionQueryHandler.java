package pl.piotrtrybala.database.query.handlers;

import pl.piotrtrybala.database.query.Query;
import pl.piotrtrybala.database.query.QueryClassifier;
import pl.piotrtrybala.database.query.QueryException;
import pl.piotrtrybala.database.query.QueryHandler;
import pl.piotrtrybala.database.query.types.ShowQuery;
import pl.piotrtrybala.database.query.types.UseCollectionQuery;

public class UseCollectionQueryHandler implements QueryHandler {
    @Override
    public boolean canParse(String query) throws QueryException {
        return QueryClassifier.isUseQuery(query);
    }

    @Override
    public Query parse(String query) throws QueryException {
        return new UseCollectionQuery(query);
    }
}
