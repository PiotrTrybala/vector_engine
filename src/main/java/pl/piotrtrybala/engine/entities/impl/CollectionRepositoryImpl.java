package pl.piotrtrybala.engine.entities.impl;

import pl.piotrtrybala.engine.entities.CollectionRepository;
import pl.piotrtrybala.engine.entities.types.Collection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollectionRepositoryImpl implements CollectionRepository {

    private Connection connection;

    public CollectionRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Collection> list(int userId) throws SQLException {

        String sql = "SELECT * FROM collections WHERE uid = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            List<Collection> collectionList = new ArrayList<>();
            stmt.setInt(1, userId);

            ResultSet result = stmt.executeQuery();
            while(result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                Timestamp createdAt = result.getTimestamp("created_at");
                Timestamp updatedAt = result.getTimestamp("updated_at");
                collectionList.add(new Collection(id, userId, name, createdAt, updatedAt));
            }
            return collectionList;
        } catch(SQLException e) {
            throw new SQLException("failed to retrieve collections");
        }
    }

    @Override
    public Collection retrieve(int id) throws SQLException {
        String sql = "SELECT * FROM collections WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                int userId = result.getInt("uid");
                String name = result.getString("name");
                Timestamp createdAt = result.getTimestamp("created_at");
                Timestamp updatedAt = result.getTimestamp("updated_at");

                return new Collection(id, userId, name, createdAt, updatedAt);
            } else {
                throw new SQLException("no collection found with id: " + id);
            }
        } catch(SQLException e) {
            throw new SQLException("failed to retrieve collection by id: " + id);
        }
    }

    @Override
    public Collection retrieve(int userId, String name) throws SQLException {
        String sql = "SELECT * FROM collections WHERE uid = ? AND name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, name);

            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                Timestamp createdAt = result.getTimestamp("created_at");
                Timestamp updatedAt = result.getTimestamp("updated_at");

                return new Collection(id, userId, name, createdAt, updatedAt);
            } else {
                throw new SQLException("no collection found with uid: " + userId);
            }
        } catch(SQLException e) {
            throw new SQLException("failed to retrieve collection by uid: " + userId);
        }
    }

    @Override
    public Collection create(int userId, String name) throws SQLException {

        String sql = "INSERT INTO collections(uid, name) VALUES (?, ?) RETURNING id, created_at, updated_at;";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, userId);
            stmt.setString(2, name);

            try (ResultSet inserted = stmt.executeQuery()) {
                if (inserted.next()) {
                    int id = inserted.getInt("id");
                    Timestamp createdAt = inserted.getTimestamp("created_at");
                    Timestamp updatedAt = inserted.getTimestamp("updated_at");

                    return new Collection(id, userId, name, createdAt, updatedAt);
                } else {
                    throw new SQLException("failed to create collection");
                }
            }
        } catch(SQLException e) {
            throw new SQLException("failed to create collection");
        }
    }

    @Override
    public Collection save(Collection collection) throws SQLException {
        return this.create(collection.getUserId(), collection.getName());
    }

    @Override
    public Collection update(Collection collection) throws SQLException {
        String sql = "UPDATE collections SET uid = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, collection.getUserId());
            stmt.setString(2, collection.getName());
            stmt.setInt(3, collection.getId());
            stmt.executeUpdate();

            return collection;
        } catch (SQLException e) {
            throw new SQLException("failed to create collection: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM collections WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("failed to delete collection: " + e.getMessage());
        }
    }

    @Override
    public void delete(int userId, String name) throws SQLException {
        String sql = "DELETE FROM collection WHERE uid = ? AND name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("failed to delete user: " + e.getMessage());
        }
    }
}
