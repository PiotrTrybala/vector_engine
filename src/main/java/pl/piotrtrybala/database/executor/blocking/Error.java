package pl.piotrtrybala.database.executor.blocking;

import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.enums.ExecutorErrorType;
import pl.piotrtrybala.database.executor.types.Result;

public class Error implements Result {

    private final ExecutorErrorType error;

    public Error(ExecutorErrorType error) {
        this.error = error;
    }

    @Override
    public char[] getResult() {
        StringBuilder builder = new StringBuilder();
        builder.append("failed to execute query: ");
        builder.append(this.error.getReason());

        return builder.toString().toCharArray();
    }
}
