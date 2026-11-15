package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Command;
import pl.piotrtrybala.database.query.CommandUtils;
import pl.piotrtrybala.database.query.enums.UseTargetType;
import pl.piotrtrybala.database.query.enums.QueryType;

import java.util.List;

public class ShowQuery implements Command {

    private UseTargetType useTargetType;

    public ShowQuery(String query) {
        List<String> parts = CommandUtils.preprocess(query);
        this.useTargetType = UseTargetType.valueOf(parts.getLast());
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.SHOW;
    }

    public UseTargetType getEntityType() {
        return useTargetType;
    }
}
