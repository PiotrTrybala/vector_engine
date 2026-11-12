package pl.piotrtrybala.database.executor.types;

import pl.piotrtrybala.database.query.Query;

public class Payload {

    private final Query query;

    public Payload(Query query) {
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
