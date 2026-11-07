package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Query;
import pl.piotrtrybala.database.query.QueryMatcher;
import pl.piotrtrybala.database.query.enums.QueryType;

import java.util.List;

public class CreateCollectionQuery implements Query {

    private final String name;

    public CreateCollectionQuery(String query) {
        List<String> parts = QueryMatcher.preprocess(query);

        this.name = parts.getLast().trim();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CreateCollectionQuery{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.CREATE_COLLECTION;
    }
}
