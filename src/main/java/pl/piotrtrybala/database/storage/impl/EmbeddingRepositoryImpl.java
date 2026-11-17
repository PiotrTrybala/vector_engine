package pl.piotrtrybala.database.storage.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.embeddings.embedder.EmbeddingUtils;
import pl.piotrtrybala.database.storage.ConnectionManager;
import pl.piotrtrybala.database.storage.MetadataUtils;
import pl.piotrtrybala.database.storage.repositories.EmbeddingRepository;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Embedding;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class EmbeddingRepositoryImpl implements EmbeddingRepository {

    private final Logger LOGGER = LogManager.getLogger(EmbeddingRepositoryImpl.class.getName());

    public EmbeddingRepositoryImpl() {
    }

    @Override
    public Optional<Embedding> findById(Integer id) throws RepositoryException {
        String sql = "SELECT * FROM embeddings WHERE id = ?";

        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                int collectionId = result.getInt("cid");
                byte[] content = result.getBytes("content");
                String contentHash = result.getString("content_hash");
                HashMap<String, Object> metadata = MetadataUtils.parse(result.getBytes("metadata"));
                String metadataHash = result.getString("metadata_hash");
                java.sql.Timestamp createdAt = result.getTimestamp("created_at");
                java.sql.Timestamp updatedAt = result.getTimestamp("updated_at");

                return Optional.of(new Embedding(id, collectionId, content, contentHash, metadata, metadataHash, createdAt, updatedAt));
            } else {
                throw new RepositoryException("embedding could not be found");
            }

        } catch (SQLException e) {
            throw new RepositoryException("failed to retrieve embedding with id: " + id);
        }
    }

    @Override
    public List<Embedding> findAll() throws RepositoryException {
        return List.of();
    }

    @Override
    public List<Embedding> findAll(int collectionId) throws RepositoryException {
        String sql = "SELECT * FROM embeddings WHERE cid = ?";

        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            List<Embedding> embeddings = new ArrayList<>();
            stmt.setInt(1, collectionId);

            try (ResultSet result = stmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    byte[] content = result.getBytes("content");
                    String contentHash = result.getString("content_hash");
                    HashMap<String, Object> metadata = MetadataUtils.parse(result.getBytes("metadata"));
                    String metadataHash = result.getString("metadata_hash");
                    Timestamp createdAt = result.getTimestamp("created_at");
                    Timestamp updatedAt = result.getTimestamp("updated_at");

                    embeddings.add(new Embedding(id, collectionId, content, contentHash, metadata, metadataHash, createdAt, updatedAt));
                }
            }

            return embeddings;
        } catch (SQLException e) {
            throw new RepositoryException("failed to retrieve embeddings");
        }
    }

    @Override
    public Integer insert(Embedding entity) throws RepositoryException {
        String sql = "INSERT INTO embeddings(cid, content, content_hash, metadata, metadata_hash) VALUES (?, ?, ?, ?, ?) RETURNING id, created_at, updated_at;";

        byte[] content = entity.getContent();
        HashMap<String, Object> metadata = entity.getMetadata();
        String contentHash = EmbeddingUtils.sha256hash(content);
        byte[] parsedMetadata = MetadataUtils.save(metadata);
        String metadataHash = EmbeddingUtils.sha256hash(parsedMetadata);

        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, entity.getCollectionId());
            stmt.setBytes(2, entity.getContent());
            stmt.setString(3, contentHash);
            stmt.setBytes(4, parsedMetadata);
            stmt.setString(5, metadataHash);

            try (ResultSet inserted = stmt.executeQuery()) {
                if (!inserted.next()) {
                    throw new RepositoryException("Failed to create embedding: no row returned");
                }
                return inserted.getInt("id");
            }
        } catch (SQLException e) {
            throw new RepositoryException("failed to insert new embedding: " + e.getMessage());
        }
    }

    @Override
    public boolean update(Embedding entity) throws RepositoryException {
        String sql = "UPDATE embeddings SET cid = ?, content = ?, content_hash = ?, metadata = ?, metadata_hash = ? WHERE id = ?";
        String contentHash = EmbeddingUtils.sha256hash(entity.getContent());
        byte[] parsedMetadata = MetadataUtils.save(entity.getMetadata());
        String metadataHash = EmbeddingUtils.sha256hash(parsedMetadata);

        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getCollectionId());
            stmt.setBytes(2, entity.getContent());
            stmt.setString(3, contentHash);
            stmt.setBytes(4, parsedMetadata);
            stmt.setString(5, metadataHash);
            stmt.setInt(6, entity.getId());

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("failed to update embedding: {}", e.getMessage());
            throw new RepositoryException("failed to update embedding");
        }
    }

    @Override
    public boolean delete(Integer id) throws RepositoryException {
        String sql = "DELETE FROM embeddings WHERE id = ?;";
        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RepositoryException("failed to update embedding");
        }
    }
}
