package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Query;
import pl.piotrtrybala.database.query.QueryMatcher;
import pl.piotrtrybala.database.query.enums.EntityType;
import pl.piotrtrybala.database.query.enums.QueryType;

import java.util.List;

public class ShowQuery implements Query {

    private EntityType entityType;

    public ShowQuery(String query) {
        List<String> parts = QueryMatcher.preprocess(query);
        this.entityType = EntityType.valueOf(parts.getLast());
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.SHOW;
    }

    public EntityType getEntityType() {
        return entityType;
    }
}
