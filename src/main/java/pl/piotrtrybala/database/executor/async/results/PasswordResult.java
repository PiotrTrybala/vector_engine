package pl.piotrtrybala.database.executor.async.results;

import pl.piotrtrybala.database.executor.async.AsyncResult;

public class PasswordResult implements AsyncResult {
    @Override
    public char[] getResult() {
        return "OK".toCharArray();
    }
}
