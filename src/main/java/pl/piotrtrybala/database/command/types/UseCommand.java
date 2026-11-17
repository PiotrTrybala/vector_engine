package pl.piotrtrybala.database.command.types;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandUtils;
import pl.piotrtrybala.database.command.enums.QueryType;

import java.util.List;

public class UseCommand implements Command {

    private final String collectionName;

    public UseCommand(String query) {
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
