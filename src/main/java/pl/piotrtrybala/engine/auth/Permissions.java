package pl.piotrtrybala.engine.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Permissions {

    private Map<String, Boolean> perms = new HashMap<>();

    public Permissions() {}
    public Permissions(HashMap<String, Boolean> perms) {
        this.perms = perms;
    }

    public static Permissions parse(String perms) throws Exception {

        if (perms == null && perms.isEmpty()) {
            throw new Exception("perms is emtpy");
        }

        List<String> parsed = List.of(perms.split(","));

        HashMap<String, Boolean> container = new HashMap<>();
        for (String perm : parsed) {
            container.put(perm, true);
        }
        return new Permissions(container);
    }

    public String save() {
        StringBuilder permissions = new StringBuilder();
        System.out.println("perms = " + perms.keySet());
        return String.join(",", perms.keySet());
    }

    public void add(String perm) {
        System.out.println("added " + perm);
        this.perms.put(perm, true);
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
