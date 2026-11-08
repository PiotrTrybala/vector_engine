package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Query;
import pl.piotrtrybala.database.query.QueryClassifier;
import pl.piotrtrybala.database.query.QueryUtils;
import pl.piotrtrybala.database.query.enums.UseTargetType;
import pl.piotrtrybala.database.query.enums.QueryType;

import java.util.List;

public class ShowQuery implements Query {

    private UseTargetType useTargetType;

    public ShowQuery(String query) {
        List<String> parts = QueryUtils.preprocess(query);
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
