package pl.piotrtrybala.database.executor.async.results;

import pl.piotrtrybala.database.executor.async.AsyncResult;

public class InsertResult implements AsyncResult {

    private final int id;

    public InsertResult(int id) {
        this.id = id;
    }

    @Override
    public char[] getResult() {
        return ("OK " + id).toCharArray();
    }
}
