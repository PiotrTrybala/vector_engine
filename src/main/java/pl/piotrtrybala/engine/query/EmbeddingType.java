package pl.piotrtrybala.engine.query;

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
