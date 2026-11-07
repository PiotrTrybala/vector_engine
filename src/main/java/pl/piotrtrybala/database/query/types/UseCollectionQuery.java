package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Query;
import pl.piotrtrybala.database.query.QueryMatcher;
import pl.piotrtrybala.database.query.enums.QueryType;

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
