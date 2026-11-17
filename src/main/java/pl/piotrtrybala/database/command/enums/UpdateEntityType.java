package pl.piotrtrybala.database.command.enums;

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
