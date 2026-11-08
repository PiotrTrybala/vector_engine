package pl.piotrtrybala.database.query;

public interface QueryHandler {
    boolean canParse(String query) throws QueryException;
    Query parse(String query) throws QueryException;
}
