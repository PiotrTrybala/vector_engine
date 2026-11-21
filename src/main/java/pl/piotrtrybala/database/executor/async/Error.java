package pl.piotrtrybala.database.executor.async;

import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.ContextResult;
import pl.piotrtrybala.database.executor.enums.ExecutorError;

public class Error implements ContextResult {

    private final ExecutorError error;

    public Error(ExecutorError error) {
        this.error = error;
    }

    @Override
    public char[] getResult(Context context) {
        String builder = "failed to execute query: " +
                this.error.getReason();

        return builder.toCharArray();
    }
}
