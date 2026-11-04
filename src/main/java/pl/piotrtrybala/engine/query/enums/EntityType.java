package pl.piotrtrybala.engine.query.enums;

public enum EntityType {
    COLLECTIONS("COLLECTIONS"), // all user collections
    USER("USER"), // current user
    COLLECTION("COLLECTION"),;// current collection

    private final String type;

    EntityType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "EntityType{" +
                "type='" + type + '\'' +
                '}';
    }
}
