package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Query;
import pl.piotrtrybala.database.query.QueryClassifier;
import pl.piotrtrybala.database.query.QueryUtils;
import pl.piotrtrybala.database.query.enums.QueryType;

import java.util.List;

public class CreateCollectionQuery implements Query {

    private final String name;

    public CreateCollectionQuery(String query) {
        List<String> parts = QueryUtils.preprocess(query);

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
