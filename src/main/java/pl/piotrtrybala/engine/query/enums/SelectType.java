package pl.piotrtrybala.engine.query.enums;

public enum SelectType {
    METADATA("METADATA"),
    TEXT("TEXT"),;

    private final String type;

    SelectType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SelectType{" +
                "type='" + type + '\'' +
                '}';
    }
}
