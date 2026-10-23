package pl.piotrtrybala.engine.query.enums;

public enum UpdateEntityType {
    METADATA("METADATA"),
    EMBEDDING("EMBEDDING"),;

    private final String keyword;

    UpdateEntityType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
