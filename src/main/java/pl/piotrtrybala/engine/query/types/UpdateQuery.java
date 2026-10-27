package pl.piotrtrybala.engine.query.types;

import pl.piotrtrybala.engine.query.*;
import pl.piotrtrybala.engine.query.enums.Keyword;
import pl.piotrtrybala.engine.query.enums.QueryType;
import pl.piotrtrybala.engine.query.enums.UpdateEntityType;
import pl.piotrtrybala.engine.query.expression.Expression;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UpdateQuery implements Query  {

    private final UpdateEntityType type;

    private HashMap<String, Object> metadata = new HashMap<>();
    private char[] data = null;

    private Expression whereClause;

    public UpdateQuery(String query) throws Exception {
        List<String> parts = QueryMatcher.preprocess(query);

        this.type = UpdateEntityType.valueOf(parts.get(1).toUpperCase());

        if (this.type == UpdateEntityType.EMBEDDING) {
            this.data = QueryUtils.parseEmbeddingData(parts);
        } else if (this.type == UpdateEntityType.METADATA){
            this.metadata = QueryUtils.parseMetadata(parts);
        }

        this.whereClause = Expression.parse(QueryUtils.skipUntilKeyword(parts, Keyword.WHERE));
    }

    public UpdateEntityType getType() {
        return type;
    }

    public HashMap<String, Object> getMetadata() {
        return metadata;
    }

    public char[] getData() {
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
