package pl.piotrtrybala.engine.query.enums;

public enum ConditionType {
    OR("OR"),
    AND("AND"),;

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
}
