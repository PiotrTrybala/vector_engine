package pl.piotrtrybala.database.executor.async.results;

import pl.piotrtrybala.database.executor.async.AsyncResult;

public class CreateResult implements AsyncResult {

    private final String name;

    public CreateResult(String name) {
        this.name = name;
    }

    @Override
    public char[] getResult() {
        return ("Created " + name).toCharArray();
    }
}
