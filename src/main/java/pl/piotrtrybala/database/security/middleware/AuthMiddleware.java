package pl.piotrtrybala.database.security.middleware;

import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.security.AuthException;

public class AuthMiddleware implements Middleware<Context> {
    @Override
    public boolean allow(Context context) throws AuthException {
        return context.isAuthenticated();
    }

    @Override
    public boolean deny(Context context) throws AuthException {
        return !context.isAuthenticated();
    }
}
