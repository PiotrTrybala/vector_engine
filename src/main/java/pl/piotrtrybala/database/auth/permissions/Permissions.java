package pl.piotrtrybala.database.auth.permissions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Permissions {

    private Map<String, Boolean> perms = new HashMap<>();

    public Permissions() {}

    private Permissions(HashMap<String, Boolean> perms) {
        this.perms = perms;
    }

    public static Permissions parse(String perms) throws Exception {

        if (perms == null || perms.isEmpty()) {
            throw new Exception("perms is emtpy");
        }

        List<String> parsed = List.of(perms.trim().split(","));

        HashMap<String, Boolean> container = new HashMap<>();
        for (String perm : parsed) {
            container.put(perm, true);
        }
        return new Permissions(container);
    }

    public String save() {
        int permsSize = perms.keySet().size();
        return String.join(",", perms.keySet());
    }

    public void add(String perm) {
        if (perm.isEmpty()) {
            return;
        }
        this.perms.put(perm.trim(), true);
    }

    public void remove(String perm) {
        perms.remove(perm);
    }

    public boolean has(String perm) {
        return perms.containsKey(perm);
    }

    @Override
    public String toString() {
        return "Permissions{" +
                "perms=" + perms.keySet() +
                '}';
    }
}
