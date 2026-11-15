package pl.piotrtrybala.database.executor.types;

import pl.piotrtrybala.database.query.Command;

public class Payload {

    private final Command query;

    public Payload(Command query) {
        this.query = query;
    }

    public Command getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return "ExecutorPayload{" +
                "query=" + query +
                '}';
    }
}
