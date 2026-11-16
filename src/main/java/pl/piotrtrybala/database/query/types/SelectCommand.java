package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Command;
import pl.piotrtrybala.database.query.CommandException;
import pl.piotrtrybala.database.query.CommandUtils;
import pl.piotrtrybala.database.query.enums.QueryType;
import pl.piotrtrybala.database.query.enums.SelectType;
import pl.piotrtrybala.database.query.expression.Expression;

import java.util.ArrayList;
import java.util.List;

public class SelectCommand implements Command {

    private final SelectType type;

    private final List<String> metadata;

    private final String collection;

    private Expression where = null;

    private final int limit;

    public SelectCommand(String query) throws CommandException {
        List<String> tokens = CommandUtils.preprocess(query);

        this.type = CommandUtils.parseSelectType(tokens);

        if (this.type == SelectType.METADATA) {
            this.metadata = CommandUtils.parseMetadataKeys(tokens);
        } else {
            this.metadata = new ArrayList<>();
        }

        this.collection = CommandUtils.parseCollectionName(tokens);

        this.where = Expression.parse(CommandUtils.retrieveExpressionTokens(tokens));

        this.limit = CommandUtils.parseLimit(tokens);
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
        return where;
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
                ", where=" + where +
                ", limit=" + limit +
                '}';
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.SELECT;
    }
}
