package pl.piotrtrybala.database.storage;

import java.util.List;

public interface Searchable<T, S> {
    List<T> search(S options) throws RepositoryException;
}
