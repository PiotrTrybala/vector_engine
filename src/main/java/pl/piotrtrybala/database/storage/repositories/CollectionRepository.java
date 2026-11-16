package pl.piotrtrybala.database.storage.repositories;

import pl.piotrtrybala.database.storage.Repository;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.Searchable;
import pl.piotrtrybala.database.storage.types.Collection;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends Repository<Collection, Integer> {
    Optional<Collection> findById(int userId, String name) throws RepositoryException;
    List<Collection> findAll(int userId) throws RepositoryException;
    void delete(int userId, String name) throws RepositoryException;
}