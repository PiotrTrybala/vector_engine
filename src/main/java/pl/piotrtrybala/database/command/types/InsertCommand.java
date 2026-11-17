package pl.piotrtrybala.database.command.types;

import pl.piotrtrybala.database.command.CommandException;
import pl.piotrtrybala.database.command.enums.EmbeddingType;
import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandUtils;
import pl.piotrtrybala.database.command.enums.QueryType;

import java.util.Arrays;
import java.util.List;

public class InsertCommand implements Command {

    private final static int COLLECTION_NAME_INDEX = 2;
    private final static int TYPE_INDEX = 4;

    private final String collectionName;
    private final EmbeddingType type;
    private final byte[] data;

    public InsertCommand(String query) throws CommandException {
        List<String> parts = CommandUtils.preprocess(query);

        this.collectionName = parts.get(COLLECTION_NAME_INDEX).trim();
        this.type = EmbeddingType.valueOf(parts.get(TYPE_INDEX).trim().toUpperCase());
        this.data = CommandUtils.parseEmbeddingData(parts);
    }

    public String getCollectionName() {
        return collectionName;
    }

    public EmbeddingType getType() {
        return type;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "InsertQuery{" +
                "collectionName='" + collectionName + '\'' +
                ", type=" + type +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.INSERT;
    }
}
