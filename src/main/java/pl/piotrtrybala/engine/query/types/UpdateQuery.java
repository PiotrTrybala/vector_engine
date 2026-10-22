package pl.piotrtrybala.engine.query.types;

import pl.piotrtrybala.engine.query.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UpdateQuery implements Query  {

    private final UpdateType type;

    private HashMap<String, Object> metadata = new HashMap<>();
    private char[] data = null;

    private WhereClause whereClause;

    public UpdateQuery(String query) throws Exception {
        List<String> parts = QueryMatcher.preprocess(query);

        this.type = UpdateType.valueOf(parts.get(1).toUpperCase());
        System.out.println("update type = " + type);

        if (this.type == UpdateType.EMBEDDING) {
            this.data = QueryUtils.parseEmbeddingData(parts);
        } else if (this.type == UpdateType.METADATA){
            this.metadata = QueryUtils.parseMetadata(parts);
        }

        this.whereClause = QueryUtils.parseWhereClause(parts);
    }

    public UpdateType getType() {
        return type;
    }

    public HashMap<String, Object> getMetadata() {
        return metadata;
    }

    public char[] getData() {
        return data;
    }

    public WhereClause getWhereClause() {
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
}
