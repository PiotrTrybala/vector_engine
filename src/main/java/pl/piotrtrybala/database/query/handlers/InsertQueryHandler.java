package pl.piotrtrybala.database.query.handlers;

import pl.piotrtrybala.database.query.Query;
import pl.piotrtrybala.database.query.QueryClassifier;
import pl.piotrtrybala.database.query.QueryException;
import pl.piotrtrybala.database.query.QueryHandler;
import pl.piotrtrybala.database.query.types.InsertQuery;
import pl.piotrtrybala.database.query.types.SelectQuery;

public class InsertQueryHandler implements QueryHandler {
    @Override
    public boolean canParse(String query) throws QueryException {
        return QueryClassifier.isSelectQuery(query);
    }

    @Override
    public Query parse(String query) throws QueryException {
        return new InsertQuery(query);
    }
}
