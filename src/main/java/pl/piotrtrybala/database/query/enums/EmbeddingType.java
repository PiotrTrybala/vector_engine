package pl.piotrtrybala.database.query.enums;

public enum EmbeddingType {
    TEXT("text"),;

    private final String type;

    EmbeddingType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
