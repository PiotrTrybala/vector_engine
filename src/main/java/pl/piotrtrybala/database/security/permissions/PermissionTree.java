package pl.piotrtrybala.database.security.permissions;

import java.util.List;

public class PermissionTree {

    private Node root;

    public PermissionTree() {
        this.root = new Node("*", true);
    }

    public void add(String permission) {

    }

    public void remove(String permission) {

    }

    public boolean hasAny(String permission) {
        return true;
    }

    public boolean hasAll(List<String> permission) {
        return true;
    }
}
