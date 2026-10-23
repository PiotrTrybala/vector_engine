package pl.piotrtrybala.engine.query.enums;

public enum MetadataValueType {
    INT("INT"),
    DOUBLE("DOUBLE"),
    TEXT("TEXT"),
    BOOLEAN("BOOLEAN"),;

    private String keyword;

    MetadataValueType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    @Override
    public String toString() {
        return "MetadataType{" +
                "keyword='" + keyword + '\'' +
                '}';
    }
}
