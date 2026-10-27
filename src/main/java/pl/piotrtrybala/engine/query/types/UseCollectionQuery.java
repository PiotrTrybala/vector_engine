package pl.piotrtrybala.engine.query.types;

import pl.piotrtrybala.engine.query.Query;
import pl.piotrtrybala.engine.query.QueryMatcher;
import pl.piotrtrybala.engine.query.enums.QueryType;

import java.util.List;

public class UseCollectionQuery implements Query {

    private final String collectionName;

    public UseCollectionQuery(String query) {
        List<String> parts = QueryMatcher.preprocess(query);
        this.collectionName = parts.getLast().trim();
    }

    public String getCollectionName() {
        return collectionName;
    }

    @Override
    public String toString() {
        return "UseCollectionQuery{" +
                "collectionName='" + collectionName + '\'' +
                '}';
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.USE;
    }
}
