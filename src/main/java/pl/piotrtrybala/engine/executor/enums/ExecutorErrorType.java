package pl.piotrtrybala.engine.executor.enums;

public enum ExecutorErrorType {

    UNSUPPORTED_QUERY_TYPE("this query type is not supported by engine"),;

    private final String reason;

    ExecutorErrorType(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
