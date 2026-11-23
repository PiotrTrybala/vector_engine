package pl.piotrtrybala.database.storage.repositories;

import pl.piotrtrybala.database.storage.Repository;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.User;

import java.util.Optional;

public interface UserRepository extends Repository<User, Integer> {
    Optional<User> findByUsername(String username) throws RepositoryException;
}
