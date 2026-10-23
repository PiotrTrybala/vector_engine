package pl.piotrtrybala.engine.query.enums;

public enum Keyword {
    HANDSHAKE("HANDSHAKE"),
    AUTH("AUTH"),
    CREATE("CREATE"),
    COLLECTION("COLLECTION"),
    USE("USE"),
    INSERT("INSERT"),
    INTO("INTO"),
    TYPE("TYPE"),
    VALUE("VALUE"),
    BEGIN("BEGIN"),
    END("END"),
    UPDATE("UPDATE"),
    EMBEDDING("EMBEDDING"),
    SELECT("SELECT"),
    WHERE("WHERE"),
    AS("AS"),
    OR("OR"),
    AND("AND"),
    METADATA("METADATA"),
    LIMIT("LIMIT"),
    ORDER("ORDER"),
    BY("BY"),
    SIMILAR("SIMILAR"),
    FROM("FROM");

    private final String keyword;

    Keyword(String keyword) {
        this.keyword = keyword;
    }
    public String getKeyword() {
        return keyword;
    }

    public static boolean is(String source, Keyword keyword) {
        return source.equals(keyword.toString());
    }

    public static boolean match(String source, Keyword keyword) throws Exception {
        try {
            Keyword sourceKeyword = Keyword.valueOf(source.trim().toUpperCase());
            return sourceKeyword.equals(keyword);
        } catch(Exception e) {
            throw new Exception("invalid keyword: " + source);
        }
    }
}
