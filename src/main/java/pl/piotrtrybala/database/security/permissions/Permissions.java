package pl.piotrtrybala.database.security.permissions;


import pl.piotrtrybala.database.security.IPermissions;
import pl.piotrtrybala.database.security.PermissionsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Permissions implements IPermissions<String, String> {
    private final Map<String, Boolean> permissions = new HashMap<>();

    @Override
    public IPermissions<String, String> parse(String permissions) throws PermissionsException {

        if (permissions == null || permissions.isEmpty()) {
            throw new PermissionsException("permissions was empty");
        }

        List<String> permissionsList = List.of(permissions.split(","));
        if (permissionsList.isEmpty()) {
            throw new PermissionsException("permissions list is empty");
        }

        Permissions p = new Permissions();
        for (String permission : permissionsList) {
            p.add(permission);
        }
        return p;
    }

    @Override
    public String save() throws PermissionsException {
        List<String> permissions = this.permissions.keySet().stream().toList();
        if (permissions.size() == 1) {
            return permissions.getFirst();
        }
        return String.join(",", permissions);
    }

    @Override
    public void add(String perm) {
        this.permissions.put(perm, true);
    }

    @Override
    public void remove(String perm) {
        this.permissions.remove(perm);
    }

    @Override
    public boolean has(String perm) {
        return this.permissions.containsKey(perm);
    }


}
