package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Command;
import pl.piotrtrybala.database.query.enums.QueryType;

public class PasswordCommand implements Command {
    @Override
    public QueryType getQueryType() {
        return QueryType.PASSWORD;
    }
}
