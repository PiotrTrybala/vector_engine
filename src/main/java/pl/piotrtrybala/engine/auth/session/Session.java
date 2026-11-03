package pl.piotrtrybala.engine.auth.session;

import pl.piotrtrybala.engine.auth.entities.User;

import java.util.Date;

public class Session {

    private final User user;
    private final Date createdAt;

    public Session(User user) {
        this.user = user;
        this.createdAt = new Date(System.currentTimeMillis());
    }

    public User getUser() {
        return user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Session{" +
                "createdAt=" + createdAt +
                '}';
    }
}
