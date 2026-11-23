package pl.piotrtrybala.database.security.permissions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Permission {
    ALL("*"),
    COLLECTION_ALL("collection.*"),
    COLLECTION_USE("collection.use"),
    COLLECTION_CREATE("collection.create"),
    COLLECTION_INSERT("collection.insert"),
    COLLECTION_UPDATE("collection.update"),
    USER_ALL("user.*"),
    USER_CREATE("user.create"),
    USER_EDIT("user.edit"),;

    private static final Map<String, Permission> PERMISSION_MAP;

    static {
        Map<String, Permission> map = new HashMap<>();
        for (Permission p : values()) {
            map.put(p.permission, p);
        }
        PERMISSION_MAP = Collections.unmodifiableMap(map);
    }

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public static Optional<Permission> from(String permission) {
        if (permission == null) {
            return Optional.empty();
        }
        return Optional.of(PERMISSION_MAP.get(permission));
    }
}
