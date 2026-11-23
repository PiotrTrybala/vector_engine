package pl.piotrtrybala.database.storage.repositories;

import pl.piotrtrybala.database.embeddings.embedder.EmbeddingUtils;
import pl.piotrtrybala.database.storage.MetadataUtils;
import pl.piotrtrybala.database.storage.Repository;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Embedding;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public interface EmbeddingRepository extends Repository<Embedding, Integer> {
    List<Embedding> findAll(int collectionId) throws RepositoryException;
    List<Embedding> findAllWithPaging(int collectionId, int limit, int offset) throws RepositoryException;
}