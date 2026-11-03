package pl.piotrtrybala.engine.auth.session;

import pl.piotrtrybala.engine.auth.entities.User;

public interface SessionStorage {

    Session retrieve(User user);
    Session create(User user);
    boolean has(User user);
    void revoke(User user);

}
