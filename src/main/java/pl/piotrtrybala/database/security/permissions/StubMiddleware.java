package pl.piotrtrybala.database.security.permissions;

import pl.piotrtrybala.database.engine.Context;

public class StubMiddleware implements Middleware<Context> {
    @Override
    public boolean allow(Context context) {
        return true;
    }

    @Override
    public boolean deny(Context context) {
        return false;
    }
}
