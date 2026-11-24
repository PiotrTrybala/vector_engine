package pl.piotrtrybala.database.security.permissions;

public interface Middleware<C> {
    boolean allow(C context);
    boolean deny(C context);
}
