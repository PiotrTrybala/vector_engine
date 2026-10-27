package pl.piotrtrybala.engine.query.types;

import pl.piotrtrybala.engine.query.enums.EmbeddingType;
import pl.piotrtrybala.engine.query.Query;
import pl.piotrtrybala.engine.query.QueryMatcher;
import pl.piotrtrybala.engine.query.QueryUtils;
import pl.piotrtrybala.engine.query.enums.QueryType;

import java.util.Arrays;
import java.util.List;

public class InsertQuery implements Query {

    private final static int COLLECTION_NAME_INDEX = 2;
    private final static int TYPE_INDEX = 4;

    private final String collectionName;
    private final EmbeddingType type;
    private final char[] data;

    public InsertQuery(String query) throws Exception {
        List<String> parts = QueryMatcher.preprocess(query);

        this.collectionName = parts.get(COLLECTION_NAME_INDEX).trim();
        this.type = EmbeddingType.valueOf(parts.get(TYPE_INDEX).trim().toUpperCase());
        this.data = QueryUtils.parseEmbeddingData(parts);
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
