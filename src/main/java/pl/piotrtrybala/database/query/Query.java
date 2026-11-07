package pl.piotrtrybala.database.query;

import pl.piotrtrybala.database.query.enums.QueryType;

public interface Query {
    QueryType getQueryType();
}
