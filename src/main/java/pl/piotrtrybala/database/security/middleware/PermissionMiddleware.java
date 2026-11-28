package pl.piotrtrybala.database.security.middleware;

import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.security.AuthException;
import pl.piotrtrybala.database.security.permissions.Permission;
import pl.piotrtrybala.database.security.permissions.Permissions;

import java.util.List;

public class PermissionMiddleware implements Middleware<Context> {

    private final List<Permission> permissions;

    public PermissionMiddleware(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean allow(Context context) throws AuthException {
        Permissions userPermissions = null;
        if (context.getSession().getUser().getPermissions() != null) {
            userPermissions = context.getSession().getUser().getPermissions();
            return userPermissions.hasAll(permissions);
        }

        Permissions groupPermissions = null;
        if (context.getSession().getUser().getGroup().getPermissions() != null) {
            groupPermissions = context.getSession().getUser().getGroup().getPermissions();
            return groupPermissions.hasAll(permissions);
        }

        return context.getSession().getUser().isRoot();
    }

    @Override
    public boolean deny(Context context) throws AuthException {
        return false;
    }

    @Override
    public String reason() {
        return "Not authorized";
    }
}
