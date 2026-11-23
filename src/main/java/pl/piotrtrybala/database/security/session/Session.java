package pl.piotrtrybala.database.security.session;

import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.storage.types.Collection;

import java.util.Date;

public class Session {

    private final String sessionToken;
    private final Date createdAt;

    private final User user;

    private Session(User user, String sessionToken) {
        this.user = user;
        this.sessionToken = sessionToken;
        this.createdAt = new Date();
    }

    public static Session create(User user) {
        return new Session(
                user,
                SessionUtils.generateSessionToken()
        );
    }

    public User getUser() {
        return user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    @Override
    public String toString() {
        return "Session{" +
                "createdAt=" + createdAt +
                ", user=" + user +
                '}';
    }
}
