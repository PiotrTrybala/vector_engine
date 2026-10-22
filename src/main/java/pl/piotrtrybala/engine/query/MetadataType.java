package pl.piotrtrybala.engine.query;

public enum MetadataType {
    INT("INT"),
    DOUBLE("DOUBLE"),
    TEXT("TEXT"),
    BOOLEAN("BOOLEAN"),;

    private String keyword;

    MetadataType(String keyword) {
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
