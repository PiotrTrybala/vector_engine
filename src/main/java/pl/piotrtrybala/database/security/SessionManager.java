package pl.piotrtrybala.database.security;

import pl.piotrtrybala.database.storage.types.User;

import java.util.Optional;

public interface SessionManager<T> {
    Optional<T> retrieve(String token);
    Optional<T> retrieve(User user);
    Optional<T> create(User user);
    void revoke(String token);
    void revoke(User user);
}
