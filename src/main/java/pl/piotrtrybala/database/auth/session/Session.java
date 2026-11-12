package pl.piotrtrybala.database.auth.session;

import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.storage.types.Collection;

import java.util.Date;

public class Session {

    private final User user;
    private final Date createdAt;

    private pl.piotrtrybala.database.storage.types.Collection currentCollection;

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

    public void setCurrentCollection(pl.piotrtrybala.database.storage.types.Collection collection){
        this.currentCollection = collection;
    }

    public Collection getCurrentCollection() {
        return currentCollection;
    }

    @Override
    public String toString() {
        return "Session{" +
                "createdAt=" + createdAt +
                '}';
    }
}
