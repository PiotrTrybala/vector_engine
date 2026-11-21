package pl.piotrtrybala.database.command.types;

import pl.piotrtrybala.database.command.*;
import pl.piotrtrybala.database.command.enums.Keyword;
import pl.piotrtrybala.database.command.enums.CommandType;
import pl.piotrtrybala.database.command.enums.UpdateEntityType;
import pl.piotrtrybala.database.command.expression.Expression;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UpdateCommand implements Command {

    private final UpdateEntityType type;

    private final String collectionName;

    private HashMap<String, Object> metadata = new HashMap<>();
    private byte[] data = null;

    private Expression where = null;

    public UpdateCommand(String query) throws CommandException {
        List<String> parts = CommandUtils.preprocess(query);

        this.collectionName = parts.get(1).trim();

        this.type = UpdateEntityType.valueOf(parts.get(3).toUpperCase());

        if (this.type == UpdateEntityType.EMBEDDING) {
            this.data = CommandUtils.parseEmbeddingData(parts);
        } else if (this.type == UpdateEntityType.METADATA){
            this.metadata = CommandUtils.parseMetadata(parts);
        }

        this.where = Expression.parse(CommandUtils.skipUntilKeyword(parts, Keyword.WHERE));
    }

    public String getCollectionName() {
        return collectionName;
    }

    public UpdateEntityType getType() {
        return type;
    }

    public HashMap<String, Object> getMetadata() {
        return metadata;
    }

    public byte[] getData() {
        return data;
    }

    public Expression getWhere() {
        return where;
    }

    @Override
    public String toString() {
        return "UpdateCommand{" +
                "type=" + type +
                ", metadata=" + metadata +
                ", data=" + Arrays.toString(data) +
                ", where=" + where +
                '}';
    }

    @Override
    public CommandType getQueryType() {
        return CommandType.UPDATE;
    }
}
