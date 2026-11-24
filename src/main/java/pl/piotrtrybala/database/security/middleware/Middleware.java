package pl.piotrtrybala.database.security.middleware;

import pl.piotrtrybala.database.security.AuthException;

public interface Middleware<C> {
    boolean allow(C context) throws AuthException;
    boolean deny(C context) throws AuthException;

    String reason();
}
