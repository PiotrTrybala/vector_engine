package pl.piotrtrybala.engine.query.types;

import pl.piotrtrybala.engine.query.Query;
import pl.piotrtrybala.engine.query.QueryMatcher;
import pl.piotrtrybala.engine.query.enums.EntityType;
import pl.piotrtrybala.engine.query.enums.QueryType;

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
