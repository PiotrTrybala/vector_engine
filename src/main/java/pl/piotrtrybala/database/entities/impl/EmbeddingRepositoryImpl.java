package pl.piotrtrybala.database.entities.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.embeddings.embedder.EmbeddingUtils;
import pl.piotrtrybala.database.entities.EmbeddingRepository;
import pl.piotrtrybala.database.entities.RepositoryException;
import pl.piotrtrybala.database.entities.types.Collection;
import pl.piotrtrybala.database.entities.types.Embedding;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class EmbeddingRepositoryImpl implements EmbeddingRepository {

    private final Connection connection;
    private final Logger LOGGER = LogManager.getLogger(EmbeddingRepositoryImpl.class.getName());

    public EmbeddingRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Embedding> list(int collectionId) throws RepositoryException {
        return List.of();
    }

    @Override
    public Embedding retrieve(int id) throws RepositoryException {
        return null;
    }

    @Override
    public Embedding retrieve(int collectionId, String name) throws RepositoryException {
        return null;
    }

    @Override
    public Embedding create(int collectionId, char[] data, HashMap<String, Object> metadata) throws RepositoryException {

        String sql = "INSERT INTO collections(cid, content, content_hash, metadata) VALUES (?, ?, ?, ?) RETURNING id, created_at, updated_at;";

        try {
            connection.getAutoCommit();
            connection.setAutoCommit(false);

            String contentHash = EmbeddingUtils.hash(data);


            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, collectionId);
                stmt.setBytes(2, EmbeddingUtils.toBytes(data));
                stmt.setString(3, contentHash);
                stmt.setString(4, metadata);

                try (ResultSet inserted = stmt.executeQuery()) {
                    if (inserted.next()) {
                        int id = inserted.getInt("id");
                        Timestamp createdAt = inserted.getTimestamp("created_at");
                        Timestamp updatedAt = inserted.getTimestamp("updated_at");

                        connection.commit();
                        return new Collection(id, collectionId, data, contentHash, metadata, metadataHash, createdAt, updatedAt);
                    } else {
                        connection.rollback();
                        throw new RepositoryException("failed to create embedding: no row returned");
                    }
                }
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RepositoryException("failed to ");
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("failed to execute auto commit: {}", e.getMessage());
            }
        }

        return null;
    }

    @Override
    public Embedding save(Embedding embedding) throws RepositoryException {
        return null;
    }

    @Override
    public Embedding update(Embedding embedding) throws RepositoryException {
        return null;
    }

    @Override
    public void delete(int id) throws RepositoryException {

    }

    @Override
    public void delete(int collectionId, String contentHash) throws RepositoryException {

    }
}
