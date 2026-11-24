package pl.piotrtrybala.database.security;

import java.util.List;

public interface IPermissions<F, P> {

    IPermissions<F, P> parse(F permissions) throws PermissionsException;
    F save() throws PermissionsException;

    void add(P perm);
    void remove(P perm);
    boolean has(P perm);

    boolean hasAny(List<P> permissions);
    boolean hasAll(List<P> permissions);

    boolean isRoot();

}
