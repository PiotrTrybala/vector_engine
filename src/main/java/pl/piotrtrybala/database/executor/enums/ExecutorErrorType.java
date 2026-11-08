package pl.piotrtrybala.database.executor.enums;

public enum ExecutorErrorType {

    UNSUPPORTED_QUERY_TYPE("this query type is not supported by engine"),
    INVALID_RESULT_TYPE("this result type is not defined correctly"),;

    private final String reason;

    ExecutorErrorType(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
