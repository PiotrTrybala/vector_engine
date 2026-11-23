package pl.piotrtrybala.database.security.session.impl;

import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.security.session.Session;
import pl.piotrtrybala.database.security.SessionManager;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManagerImpl implements SessionManager<Session> {

    private final Map<String, Session> sessions;

    public SessionManagerImpl() {
        this.sessions = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<Session> retrieve(User user) {
        if (user == null) {
            return Optional.empty();
        }

        for (String token : sessions.keySet()) {
            Session session = this.sessions.get(token);
            if (session != null && session.getUser().equals(user)) {
                return Optional.of(session);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Session> retrieve(String sessionToken) {
        return Optional.of(this.sessions.get(sessionToken));
    }


    @Override
    public Optional<Session> create(User user) {
        if (user == null) {
            return Optional.empty();
        }
        Session session = Session.create(user);
        this.sessions.put(session.getSessionToken(), session);
        return Optional.of(session);
    }

    @Override
    public boolean has(User user) {
        return retrieve(user).isPresent();
    }

    @Override
    public boolean valid(String sessionToken) {
        return sessionToken != null && this.sessions.containsKey(sessionToken);
    }

    @Override
    public void revoke(User user) {
        if (user == null) {
            return;
        }
        sessions.entrySet().removeIf(entry -> {
            Session session = entry.getValue();
            return session != null && session.getUser() != null && session.getUser().getId() == user.getId();
        });
    }
}
