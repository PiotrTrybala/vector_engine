package pl.piotrtrybala.database.storage.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.embeddings.embedder.EmbeddingUtils;
import pl.piotrtrybala.database.storage.repositories.EmbeddingRepository;
import pl.piotrtrybala.database.storage.MetadataUtils;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Embedding;

import java.sql.*;
import java.util.ArrayList;
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

        String sql = "SELECT * FROM embeddings WHERE cid = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            List<Embedding> embeddings = new ArrayList<>();
            stmt.setInt(1, collectionId);

            ResultSet result = stmt.executeQuery();
            while(result.next()) {
                int id=  result.getInt("id");
                byte[] content = result.getBytes("content");
                String contentHash = result.getString("content_hash");
                HashMap<String, Object> metadata = MetadataUtils.parse(result.getBytes("metadata"));
                String metadataHash = result.getString("metadata_hash");
                Timestamp createdAt = result.getTimestamp("created_at");
                Timestamp updatedAt = result.getTimestamp("updated_at");

                embeddings.add(new Embedding(id, collectionId, content, contentHash, metadata, metadataHash, createdAt, updatedAt));
            }
            return embeddings;
        } catch(SQLException e) {
            throw new RepositoryException("failed to retrieve collections");
        }
    }

    @Override
    public Embedding retrieve(int id) throws RepositoryException {

        String sql = "SELECT * FROM embeddings WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                int collectionId =  result.getInt("cid");
                byte[] content = result.getBytes("content");
                String contentHash = result.getString("content_hash");
                HashMap<String, Object> metadata = MetadataUtils.parse(result.getBytes("metadata"));
                String metadataHash = result.getString("metadata_hash");
                Timestamp createdAt = result.getTimestamp("created_at");
                Timestamp updatedAt = result.getTimestamp("updated_at");

                return new Embedding(id, collectionId, content, contentHash, metadata, metadataHash, createdAt, updatedAt);
            } else {
                throw new RepositoryException("embedding could not be found");
            }

        } catch(SQLException e) {
            throw new RepositoryException("failed to retrieve embedding with id: " + id);
        }
    }

    @Override
    public Embedding retrieve(int collectionId, String contentHash) throws RepositoryException {
        String sql = "SELECT * FROM embeddings WHERE cid = ? AND content_hash = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, collectionId);
            stmt.setString(2, contentHash);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                int id =  result.getInt("id");
                byte[] content = result.getBytes("content");
                HashMap<String, Object> metadata = MetadataUtils.parse(result.getBytes("metadata"));
                String metadataHash = result.getString("metadata_hash");
                Timestamp createdAt = result.getTimestamp("created_at");
                Timestamp updatedAt = result.getTimestamp("updated_at");

                return new Embedding(id, collectionId, content, contentHash, metadata, metadataHash, createdAt, updatedAt);
            } else {
                throw new RepositoryException("embedding could not be found");
            }

        } catch(SQLException e) {
            throw new RepositoryException("failed to retrieve embedding with cid: " + collectionId);
        }
    }

    @Override
    public Embedding create(int collectionId, byte[] data, HashMap<String, Object> metadata) throws RepositoryException {

        String sql = "INSERT INTO embeddings(cid, content, content_hash, metadata, metadata_hash) VALUES (?, ?, ?, ?, ?) RETURNING id, created_at, updated_at;";

        try {
            connection.getAutoCommit();
            connection.setAutoCommit(false);

            int id;
            String contentHash = EmbeddingUtils.hash(data);
            byte[] parsedMetadata = MetadataUtils.save(metadata);
            String metadataHash = EmbeddingUtils.hash(parsedMetadata);
            Timestamp createdAt;
            Timestamp updatedAt;

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, collectionId);
                stmt.setBytes(2, data);
                stmt.setString(3, contentHash);
                stmt.setBytes(4, parsedMetadata);
                stmt.setString(5, metadataHash);

                try (ResultSet inserted = stmt.executeQuery()) {
                    if (inserted.next()) {
                        id = inserted.getInt("id");
                        createdAt = inserted.getTimestamp("created_at");
                        updatedAt = inserted.getTimestamp("updated_at");
                    } else {
                        connection.rollback();
                        throw new RepositoryException("failed to create embedding: no row returned");
                    }
                }
            }

            connection.commit();
            return new Embedding(id, collectionId, data, contentHash, metadata, metadataHash, createdAt, updatedAt);
        } catch (SQLException e) {
            LOGGER.error("failed to commit transaction: {}", e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RepositoryException("failed to ");
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("failed to insert embedding: {}", e.getMessage());
            }
        }

        return null;
    }

    @Override
    public Embedding save(Embedding embedding) throws RepositoryException {
        return this.create(
                embedding.getCollectionId(),
                embedding.getContent(),
                embedding.getMetadata()
                );
    }

    @Override
    public Embedding update(Embedding embedding) throws RepositoryException {
        String sql = "UPDATE embeddings SET collectionId = ?, content = ?, content_hash = ?, metadata = ?, metadata_hash = ? WHERE id = ?";
        try {
            connection.getAutoCommit();
            connection.setAutoCommit(false);

            String contentHash = EmbeddingUtils.hash(embedding.getContent());
            byte[] parsedMetadata = MetadataUtils.save(embedding.getMetadata());
            String metadataHash = EmbeddingUtils.hash(parsedMetadata);

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, embedding.getCollectionId());
                stmt.setBytes(2, embedding.getContent());
                stmt.setString(3, contentHash);
                stmt.setBytes(4, parsedMetadata);
                stmt.setString(5, metadataHash);
                stmt.setInt(6, embedding.getId());

                stmt.executeUpdate();

            } catch(SQLException e) {
                LOGGER.error("failed to update embedding: {}", e.getMessage());
                throw new RepositoryException("failed to update embedding");
            }

            connection.commit();
            return embedding;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RepositoryException("failed to rollback: " + e.getMessage());
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
    public void delete(int id) throws RepositoryException {
        String sql = "DELETE FROM embeddings WHERE id = ?;";
        try {
            connection.getAutoCommit();
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();

            } catch(SQLException e) {
                LOGGER.error("failed to update embedding: {}", e.getMessage());
                throw new RepositoryException("failed to update embedding");
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RepositoryException("failed to rollback: " + e.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("failed to execute auto commit: {}", e.getMessage());
            }
        }
    }

    @Override
    public void delete(int collectionId, String contentHash) throws RepositoryException {
        String sql = "DELETE FROM embeddings WHERE cid = ? AND content_hash = ?;";
        try {
            connection.getAutoCommit();
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, collectionId);
                stmt.setString(2, contentHash);
                stmt.executeUpdate();

            } catch(SQLException e) {
                LOGGER.error("failed to update embedding: {}", e.getMessage());
                throw new RepositoryException("failed to update embedding");
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RepositoryException("failed to rollback: " + e.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("failed to execute auto commit: {}", e.getMessage());
            }
        }
    }
}
