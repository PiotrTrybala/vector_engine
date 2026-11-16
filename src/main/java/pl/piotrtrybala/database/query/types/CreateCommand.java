package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Command;
import pl.piotrtrybala.database.query.CommandUtils;
import pl.piotrtrybala.database.query.enums.QueryType;

import java.util.List;

public class CreateCommand implements Command {

    private final String name;

    public CreateCommand(String query) {
        List<String> parts = CommandUtils.preprocess(query);

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
