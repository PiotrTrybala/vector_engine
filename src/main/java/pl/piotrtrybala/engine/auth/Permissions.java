package pl.piotrtrybala.engine.auth;

import java.util.HashMap;
import java.util.List;

public class Permissions {

    private final HashMap<String, Boolean> perms = new HashMap<>();

    public Permissions(HashMap<String, Boolean> perms) {}

    public static Permissions parse(String perms) {

        List<String> parsed = List.of(perms.split(","));

        HashMap<String, Boolean> container = new HashMap<>();
        for (String perm : parsed) {
            container.put(perm, true);
        }
        return new Permissions(container);
    }

    public void add(String perm) {
        this.perms.put(perm, true);
    }

    public void remove(String perm) {
        perms.remove(perm);
    }

    public boolean has(String perm) {
        return perms.containsKey(perm);
    }

}
