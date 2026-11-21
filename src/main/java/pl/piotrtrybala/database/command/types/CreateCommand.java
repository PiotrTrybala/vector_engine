package pl.piotrtrybala.database.command.types;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandUtils;
import pl.piotrtrybala.database.command.enums.CommandType;

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
    public CommandType getQueryType() {
        return CommandType.CREATE_COLLECTION;
    }
}
