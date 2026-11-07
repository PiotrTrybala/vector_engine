package pl.piotrtrybala.database.executor.types;

import pl.piotrtrybala.database.executor.enums.ExecutorErrorType;

public class ExecutorError implements ExecutorResult {

    private ExecutorErrorType error;

    public ExecutorError(ExecutorErrorType error) {
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
