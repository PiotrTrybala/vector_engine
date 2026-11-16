package pl.piotrtrybala.database.storage;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

    Optional<T> findById(ID id) throws RepositoryException;

    List<T> findAll() throws RepositoryException;

    <S> List<T> search(S options) throws RepositoryException;

    ID insert(T entity) throws RepositoryException;

    boolean update(T entity) throws RepositoryException;

    boolean delete(ID id) throws RepositoryException;

}
