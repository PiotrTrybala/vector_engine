package pl.piotrtrybala.database.auth.session;

import pl.piotrtrybala.database.storage.types.User;

public interface Multitenancy {
    Session retrieve(String sessionToken);
    Session create(User user);
    void revoke(String sessionToken);
}
