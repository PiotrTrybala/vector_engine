package pl.piotrtrybala.database.security;

import java.util.Optional;

public interface UserManager<T> {
    Optional<T> retrieve(int id);
    Optional<T> retrieve(String username);
}
