package pl.piotrtrybala.database.storage.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.auth.permissions.Permissions;
import pl.piotrtrybala.database.storage.ConnectionManager;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.storage.repositories.UserRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = LogManager.getLogger(UserRepositoryImpl.class.getName());

    @Override
    public Optional<User> findById(Integer id) throws RepositoryException {

        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) throw new RepositoryException("could not found user: " + id);
                String username = result.getString("username");
                String password = result.getString("password");
                boolean root = result.getBoolean("root");
                String rawPerms = result.getString("perms");
                Permissions permissions = Permissions.parse(rawPerms);
                Timestamp createdAt = result.getTimestamp("created_at");
                Timestamp updatedAt = result.getTimestamp("updated_at");

                return Optional.of(new User(id, username, password, root, permissions, createdAt, updatedAt));
            }
        } catch (SQLException e) {
            throw new RepositoryException("failed to retrieve user by id: " + id);
        } catch (Exception e) {
            throw new RepositoryException("failed to parse perms: " + e.getMessage());
        }
    }
    @Override
    public Optional<User> findByUsername(String username) throws RepositoryException {
        String sql = "SELECT * FROM users WHERE username = ?";
        LOGGER.info("looking for user with username: {}", username);
        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) throw new RepositoryException("could not found user: " + username);
                int id = result.getInt("id");
                String password = result.getString("password");
                boolean root = result.getBoolean("root");
                String rawPerms = result.getString("perms");
                Permissions permissions = Permissions.parse(rawPerms);
                Timestamp createdAt = result.getTimestamp("created_at");
                Timestamp updatedAt = result.getTimestamp("updated_at");

                return Optional.of(new User(id, username, password, root, permissions, createdAt, updatedAt));
            }
        } catch (SQLException e) {
            throw new RepositoryException("failed to retrieve user by username: " + username);
        } catch (Exception e) {
            throw new RepositoryException("failed to parse perms: " + e.getMessage());
        }
    }

    @Override
    public List<User> findAll() throws RepositoryException {
        return List.of();
    }

    @Override
    public Integer insert(User entity) throws RepositoryException {
        String sql = "INSERT INTO users(username, password, root, perms) VALUES (?, ?, ?, ?) RETURNING id;";
        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getPassword());
            stmt.setBoolean(3, entity.isRoot());
            stmt.setString(4, entity.getPermissions().save());

            try (ResultSet inserted = stmt.executeQuery()) {
                if (inserted.next()) {
                    return inserted.getInt("id");
                } else {
                    throw new SQLException("failed to create user, no id");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("failed to create user: " + e.getMessage());
        }
    }

    @Override
    public boolean update(User entity) throws RepositoryException {

        String sql = "UPDATE users SET username = ?, password = ?, root = ?, perms = ? WHERE id = ?";
        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getPassword());
            stmt.setBoolean(3, entity.isRoot());
            stmt.setString(4, entity.getPermissions().save());
            stmt.setInt(5, entity.getId());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RepositoryException("failed to create user: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Integer id) throws RepositoryException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RepositoryException("failed to delete user: " + e.getMessage());
        }
    }

    public void delete(String username) throws RepositoryException {
        String sql = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("failed to delete user: " + e.getMessage());
        }
    }
}
