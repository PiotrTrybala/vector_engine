package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Command;
import pl.piotrtrybala.database.query.CommandUtils;
import pl.piotrtrybala.database.query.enums.QueryType;

import java.util.List;

public class UseCollectionQuery implements Command {

    private final String collectionName;

    public UseCollectionQuery(String query) {
        List<String> parts = CommandUtils.preprocess(query);
        this.collectionName = parts.getLast().trim();

        System.out.println("collection name = " + collectionName);
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
        return QueryType.USE_COLLECTION;
    }
}
