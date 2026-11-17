package pl.piotrtrybala.database.command.enums;

public enum ConditionType {
    OR("OR"),
    AND("AND"),
    NONE("NONE");

    private final String keyword;

    ConditionType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    @Override
    public String toString() {
        return "ConditionType{" +
                "keyword='" + keyword + '\'' +
                '}';
    }

    public static boolean is(String token, ConditionType condition) {
        return condition.keyword.equals(token.toUpperCase());
    }
}
