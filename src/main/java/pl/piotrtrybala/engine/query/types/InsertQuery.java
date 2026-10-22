package pl.piotrtrybala.engine.query.types;

import pl.piotrtrybala.engine.query.EmbeddingType;
import pl.piotrtrybala.engine.query.Query;
import pl.piotrtrybala.engine.query.QueryMatcher;

import java.util.List;

public class InsertQuery implements Query {

    private final static int COLLECTION_NAME_INDEX = 2;
    private final static int TYPE_INDEX = 4;
    private final static int DATA_INDEX = 6;

    private final String collectionName;
    private final EmbeddingType type;
    private final char[] data;

    public InsertQuery(String query) throws Exception {
        List<String> parts = QueryMatcher.preprocess(query);

        this.collectionName = parts.get(COLLECTION_NAME_INDEX).trim();
        this.type = EmbeddingType.valueOf(parts.get(TYPE_INDEX).trim().toUpperCase());
        this.data = this.parseData(parts.get(DATA_INDEX).trim());
    }

    private char[] parseData(String data) throws Exception {
        if (data.startsWith("`") && data.endsWith("`")) {
            return data.substring(1, data.length() - 1).toCharArray();
        }
        throw new Exception("invalid data definition");
    }

    public String getCollectionName() {
        return collectionName;
    }

    public EmbeddingType getType() {
        return type;
    }

    public char[] getData() {
        return data;
    }
}
