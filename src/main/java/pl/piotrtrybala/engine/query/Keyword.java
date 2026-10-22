package pl.piotrtrybala.engine.query;

public enum Keyword {
    HANDSHAKE("HANDSHAKE"),
    AUTH("AUTH"),
    CREATE("CREATE"),
    COLLECTION("COLLECTION"),
    USE("USE"),
    INSERT("INSERT"),
    INTO("INTO"),
    TEXT("TEXT"),
    BEGIN("BEGIN"),
    END("END"),
    UPDATE("UPDATE"),
    EMBEDDING("EMBEDDING"),
    WHERE("WHERE"),
    OR("OR"),
    AND("AND"),
    METADATA("METADATA"),
    LIMIT("LIMIT"),
    ORDER("ORDER"),
    BY("BY"),
    SIMILAR("SIMILAR");

    private final String keyword;

    Keyword(String keyword) {
        this.keyword = keyword;
    }
    public String getKeyword() {
        return keyword;
    }

    public boolean match(String source, Keyword keyword) throws Exception {
        try {
            Keyword sourceKeyword = Keyword.valueOf(source.trim().toUpperCase());
            return sourceKeyword.equals(keyword);
        } catch(Exception e) {
            throw new Exception("invalid keyword: " + source);
        }
    }
}
