package pl.piotrtrybala.database.security;

public interface IPermissions<F, P> {

    IPermissions<F, P> parse(F permissions) throws PermissionsException;
    F save() throws PermissionsException;

    void add(P perm);
    void remove(P perm);
    boolean has(P perm);

}
