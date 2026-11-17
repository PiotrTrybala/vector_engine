package pl.piotrtrybala.database.storage.impl;

import pl.piotrtrybala.database.storage.ConnectionManager;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.repositories.CollectionRepository;
import pl.piotrtrybala.database.storage.types.Collection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionRepositoryImpl implements CollectionRepository {

    public CollectionRepositoryImpl() {
    }

    @Override
    public Optional<Collection> findById(Integer id) throws RepositoryException {

        String sql = "SELECT * FROM collections WHERE id = ?";

        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) throw new RepositoryException("failed to retrieve collection: " + id);
                int userId = result.getInt("uid");
                String name = result.getString("name");
                Timestamp createdAt = result.getTimestamp("created_at");
                Timestamp updatedAt = result.getTimestamp("updated_at");

                return Optional.of(new Collection(id, userId, name, createdAt, updatedAt));
            }
        } catch (SQLException e) {
            throw new RepositoryException("failed to retrieve collection: " + id);
        }
    }

    @Override
    public Optional<Collection> findById(int userId, String name) throws RepositoryException {
        String sql = "SELECT * FROM collections WHERE uid = ? AND name = ?";
        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, name);

            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) throw new RepositoryException("could not find collection: " + name);
                int id = result.getInt("id");
                Timestamp createdAt = result.getTimestamp("created_at");
                Timestamp updatedAt = result.getTimestamp("updated_at");

                return Optional.of(new Collection(id, userId, name, createdAt, updatedAt));
            }
        } catch (SQLException e) {
            throw new RepositoryException("could not find collection: " + name);
        }
    }

    @Override
    public List<Collection> findAll() throws RepositoryException {
        return List.of();
    }

    @Override
    public List<Collection> findAll(int userId) throws RepositoryException {

        String sql = "SELECT * FROM collections WHERE uid = ?";
        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            List<Collection> collections = new ArrayList<>();

            stmt.setInt(1, userId);
            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) throw new RepositoryException("could not retrieve collections");
                int id = result.getInt("id");
                String name = result.getString("name");
                java.sql.Timestamp createdAt = result.getTimestamp("created_at");
                Timestamp updatedAt = result.getTimestamp("updated_at");
                collections.add(new Collection(id, userId, name, createdAt, updatedAt));
            }

            return collections;
        } catch (SQLException e) {
            throw new RepositoryException("failed to retrieve collections");
        }
    }

    @Override
    public Integer insert(Collection entity) throws RepositoryException {
        String sql = "INSERT INTO collections(uid, name) VALUES (?, ?) RETURNING id, created_at, updated_at;";

        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, entity.getUserId());
            stmt.setString(2, entity.getName());

            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) throw new RepositoryException("failed to create collection");
                return result.getInt("id");

            }
        } catch (SQLException e) {
            throw new RepositoryException("failed to create collection");
        }
    }

    @Override
    public boolean update(Collection entity) throws RepositoryException {
        String sql = "UPDATE collections SET uid = ? WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, entity.getUserId());
            stmt.setString(2, entity.getName());
            stmt.setInt(3, entity.getId());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RepositoryException("failed to create collection: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Integer id) throws RepositoryException {
        String sql = "DELETE FROM collections WHERE id = ?";
        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RepositoryException("failed to delete collection: " + e.getMessage());
        }
    }

    @Override
    public void delete(int userId, String name) throws RepositoryException {
        String sql = "DELETE FROM collection WHERE uid = ? AND name = ?";
        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("failed to delete user: " + e.getMessage());
        }
    }
}
