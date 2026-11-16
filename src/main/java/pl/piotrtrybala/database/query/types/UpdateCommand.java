package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.*;
import pl.piotrtrybala.database.query.enums.Keyword;
import pl.piotrtrybala.database.query.enums.QueryType;
import pl.piotrtrybala.database.query.enums.UpdateEntityType;
import pl.piotrtrybala.database.query.expression.Expression;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UpdateCommand implements Command {

    private final UpdateEntityType type;

    private HashMap<String, Object> metadata = new HashMap<>();
    private byte[] data = null;

    private Expression whereClause;

    public UpdateCommand(String query) throws CommandException {
        List<String> parts = CommandUtils.preprocess(query);

        this.type = UpdateEntityType.valueOf(parts.get(1).toUpperCase());

        if (this.type == UpdateEntityType.EMBEDDING) {
            this.data = CommandUtils.parseEmbeddingData(parts);
        } else if (this.type == UpdateEntityType.METADATA){
            this.metadata = CommandUtils.parseMetadata(parts);
        }

        this.whereClause = Expression.parse(CommandUtils.skipUntilKeyword(parts, Keyword.WHERE));
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

    public Expression getWhereClause() {
        return whereClause;
    }

    @Override
    public String toString() {
        return "UpdateQuery{" +
                "type=" + type +
                ", metadata=" + metadata +
                ", data=" + Arrays.toString(data) +
                ", whereClause=" + whereClause +
                '}';
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.UPDATE;
    }
}
