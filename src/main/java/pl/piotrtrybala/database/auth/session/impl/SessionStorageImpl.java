package pl.piotrtrybala.database.auth.session.impl;

import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.auth.session.SessionStorage;

public class SessionStorageImpl implements SessionStorage {

    public Session session;

    @Override
    public Session retrieve(User user) {
        return this.session;
    }

    @Override
    public Session create(User user) {
        this.session = new Session(user);
        return session;
    }

    @Override
    public boolean has(User user) {
        if (session == null) return false;
        return !session.getUser().equals(user);
    }

    @Override
    public void revoke(User user) {

    }
}
