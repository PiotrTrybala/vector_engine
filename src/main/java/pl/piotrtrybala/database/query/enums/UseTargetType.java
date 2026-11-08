package pl.piotrtrybala.database.query.enums;

public enum UseTargetType {
    COLLECTIONS("COLLECTIONS"), // all user collections
    USER("USER"), // current user
    COLLECTION("COLLECTION"),;// current collection

    private final String target;

    UseTargetType(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "UseTargetType{" +
                "target='" + target + '\'' +
                '}';
    }
}
