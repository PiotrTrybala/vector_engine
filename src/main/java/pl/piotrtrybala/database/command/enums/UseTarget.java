package pl.piotrtrybala.database.command.enums;

public enum UseTarget {
    COLLECTIONS("COLLECTIONS"), // all user collections
    USER("USER"), // current user
    COLLECTION("COLLECTION"),
    EMBEDDINGS("EMBEDDINGS"),;// current collection

    private final String target;

    UseTarget(String target) {
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
