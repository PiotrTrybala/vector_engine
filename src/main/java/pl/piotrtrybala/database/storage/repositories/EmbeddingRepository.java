package pl.piotrtrybala.database.storage.repositories;

import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Embedding;

import java.util.HashMap;
import java.util.List;

public interface EmbeddingRepository {

    List<Embedding> list(int collectionId) throws RepositoryException;

    Embedding retrieve(int id) throws RepositoryException;

    Embedding retrieve(int collectionId, String name) throws RepositoryException;

    Embedding create(int collectionId, byte[] data, HashMap<String, Object> metadata) throws RepositoryException;

    Embedding save(Embedding embedding) throws RepositoryException;

    Embedding update(Embedding embedding) throws RepositoryException;

    void delete(int id) throws RepositoryException;

    void delete(int collectionId, String contentHash) throws RepositoryException;

}
