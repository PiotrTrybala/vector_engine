package pl.piotrtrybala.database.security.permissions;

import pl.piotrtrybala.database.engine.Context;

public class AuthMiddleware implements Middleware<Context>{
    @Override
    public boolean allow(Context context) {
        return context.isAuthenticated();
    }

    @Override
    public boolean deny(Context context) {
        return !context.isAuthenticated();
    }
}
