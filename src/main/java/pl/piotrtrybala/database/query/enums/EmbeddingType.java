package pl.piotrtrybala.database.query.enums;

public enum EmbeddingType {
    TEXT("text"),;

    private final String keyword;

    EmbeddingType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
