package pl.piotrtrybala.database.storage;

import java.util.List;

public interface Searchable<T, ID, S> {
    List<T> search(S options) throws RepositoryException;
}
