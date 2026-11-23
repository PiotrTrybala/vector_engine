package pl.piotrtrybala.database.security;

import pl.piotrtrybala.database.storage.types.User;

import java.util.Optional;

public interface SessionManager<S> {

    Optional<S> retrieve(User user);
    Optional<S> retrieve(String sessionToken);
    Optional<S> create(User user);

    boolean has(User user);
    boolean valid(String sessionToken);
    void revoke(User user);

}
