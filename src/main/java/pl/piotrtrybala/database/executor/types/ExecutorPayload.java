package pl.piotrtrybala.database.executor.types;

import pl.piotrtrybala.database.query.Query;

public class ExecutorPayload {

    private final Query query;

    public ExecutorPayload(Query query) {
        this.query = query;
    }

    public Query getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return "ExecutorPayload{" +
                "query=" + query +
                '}';
    }
}
