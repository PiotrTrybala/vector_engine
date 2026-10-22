package pl.piotrtrybala.engine.query.types;

import pl.piotrtrybala.engine.query.Query;
import pl.piotrtrybala.engine.query.QueryMatcher;

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
}
