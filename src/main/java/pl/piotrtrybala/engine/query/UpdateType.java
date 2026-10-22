package pl.piotrtrybala.engine.query;

public enum UpdateType {
    METADATA("METADATA"),
    EMBEDDING("EMBEDDING"),;

    private final String keyword;

    UpdateType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
