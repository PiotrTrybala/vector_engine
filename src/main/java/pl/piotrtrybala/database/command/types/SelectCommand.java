package pl.piotrtrybala.database.command.types;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandException;
import pl.piotrtrybala.database.command.CommandUtils;
import pl.piotrtrybala.database.command.enums.QueryType;
import pl.piotrtrybala.database.command.enums.SelectType;
import pl.piotrtrybala.database.command.expression.Expression;

import java.util.ArrayList;
import java.util.List;

public class SelectCommand implements Command {

    private final SelectType type;

    private final List<String> metadata;

    private final String collectionName;

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

        this.collectionName = CommandUtils.parseCollectionName(tokens);

        this.where = Expression.parse(CommandUtils.retrieveExpressionTokens(tokens));

        this.limit = CommandUtils.parseLimit(tokens);
    }

    public SelectType getType() {
        return type;
    }

    public List<String> getMetadata() {
        return metadata;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public Expression getWhere() {
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
                ", collectionName='" + collectionName + '\'' +
                ", where=" + where +
                ", limit=" + limit +
                '}';
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.SELECT;
    }
}
