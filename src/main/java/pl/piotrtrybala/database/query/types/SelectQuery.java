package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Query;
import pl.piotrtrybala.database.query.QueryMatcher;
import pl.piotrtrybala.database.query.QueryUtils;
import pl.piotrtrybala.database.query.enums.QueryType;
import pl.piotrtrybala.database.query.enums.SelectType;
import pl.piotrtrybala.database.query.expression.Expression;

import java.util.ArrayList;
import java.util.List;

public class SelectQuery implements Query {

    private final SelectType type;

    private final List<String> metadata;

    private final String collection;

    private Expression expression = null;

    private final int limit;

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

    @Override
    public QueryType getQueryType() {
        return QueryType.SELECT;
    }
}
