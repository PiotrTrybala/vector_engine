package pl.piotrtrybala.database.security.permissions;

import pl.piotrtrybala.database.security.IPermissions;
import pl.piotrtrybala.database.security.PermissionsException;

import java.util.List;

public class GroupedPermissions implements IPermissions<String, String> {

    private PermissionTree tree;

    private GroupedPermissions() {
        this.tree = new PermissionTree();
    }

    @Override
    public IPermissions<String, String> parse(String permissions) throws PermissionsException {
        return null;
    }

    @Override
    public String save() throws PermissionsException {
        return "";
    }

    @Override
    public void add(String perm) {

    }

    @Override
    public void remove(String perm) {

    }

    @Override
    public boolean has(String perm) {
        return false;
    }

    @Override
    public boolean hasAny(List<String> permissions) {
        return false;
    }

    @Override
    public boolean hasAll(List<String> permissions) {
        return false;
    }

    @Override
    public boolean isRoot() {
        return false;
    }
}
