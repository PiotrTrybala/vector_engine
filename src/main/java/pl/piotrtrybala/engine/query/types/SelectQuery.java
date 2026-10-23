package pl.piotrtrybala.engine.query.types;

import pl.piotrtrybala.engine.query.Query;
import pl.piotrtrybala.engine.query.QueryMatcher;
import pl.piotrtrybala.engine.query.QueryUtils;
import pl.piotrtrybala.engine.query.enums.SelectType;
import pl.piotrtrybala.engine.query.expression.Expression;

import java.util.ArrayList;
import java.util.List;

public class SelectQuery implements Query {

    private final SelectType type;

    private final List<String> metadata;

    private final String collection;

    private Expression expression = null;

    private int limit;

    public SelectQuery(String query) throws Exception {
        List<String> tokens = QueryMatcher.preprocess(query);

        this.type = QueryUtils.parseSelectType(tokens);

        if (this.type == SelectType.METADATA) {
            this.metadata = QueryUtils.parseMetadataKeys(tokens);
        } else {
            this.metadata = new ArrayList<>();
        }

        this.collection = QueryUtils.parseCollectionName(tokens);

        this.expression = Expression.parse(QueryUtils.retrieveExpressionTokens(tokens));

        this.limit = QueryUtils.parseLimit(tokens);
    }

    public SelectType getType() {
        return type;
    }

    public List<String> getMetadata() {
        return metadata;
    }

    public String getCollection() {
        return collection;
    }

    public Expression getExpression() {
        return expression;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public String toString() {
        return "SelectQuery{" +
                "type=" + type +
                ", metadata=" + metadata +
                ", collection='" + collection + '\'' +
                ", expression=" + expression +
                ", limit=" + limit +
                '}';
    }
}
