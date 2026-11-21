package pl.piotrtrybala.database.executor.sync;

import pl.piotrtrybala.database.executor.enums.ExecutorError;
import pl.piotrtrybala.database.executor.Result;

public class Error implements Result {

    private final ExecutorError error;

    public Error(ExecutorError error) {
        this.error = error;
    }

    @Override
    public char[] getResult() {
        String builder = "failed to execute query: " +
                this.error.getReason();

        return builder.toCharArray();
    }
}
