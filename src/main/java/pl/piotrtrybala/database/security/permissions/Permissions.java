package pl.piotrtrybala.database.security.permissions;


import pl.piotrtrybala.database.security.IPermissions;
import pl.piotrtrybala.database.security.PermissionsException;

import java.util.*;

public class Permissions implements IPermissions<String, Permission> {
    private final Map<Permission, Boolean> permissions = new HashMap<>();

    @Override
    public IPermissions<String, Permission> parse(String permissions) throws PermissionsException {

        if (permissions == null || permissions.isEmpty()) {
            return new Permissions();
        }

        List<Permission> permissionList = Arrays.
                stream(permissions.split(","))
                .map(p -> {
                    Optional<Permission> permission = Permission.from(p);
                    if (permission.isEmpty()) throw new PermissionsException("undefined permission: " + p);
                    return permission.get();
                }).toList();

        Permissions perms = new Permissions();
        for (Permission perm : permissionList) {
            perms.add(perm);
        }
        return perms;
    }

    @Override
    public String save() throws PermissionsException {
        List<String> permissions = this.permissions.keySet().stream().map(Permission::getPermission).toList();
        if (permissions.size() == 1) {
            return permissions.getFirst();
        }
        return String.join(",", permissions);
    }

    @Override
    public void add(Permission perm) {
        this.permissions.put(perm, true);
    }

    @Override
    public void remove(Permission perm) {
        this.permissions.remove(perm);
    }

    @Override
    public boolean has(Permission perm) {
        return this.permissions.containsKey(perm);
    }

    @Override
    public boolean hasAny(List<Permission> permissions) {
        for (Permission permission : permissions) {
            if (this.has(permission)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasAll(List<Permission> permissions) {
        int count = 0;
        for (Permission permission : permissions) {
            if (this.has(permission)) count++;
        }
        return count == permissions.size();
    }


}
