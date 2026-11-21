package pl.piotrtrybala.database.executor.async;

import pl.piotrtrybala.database.executor.enums.ExecutorError;

public class Error implements AsyncResult {

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
