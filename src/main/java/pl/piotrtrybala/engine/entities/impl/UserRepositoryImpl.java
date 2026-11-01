package pl.piotrtrybala.engine.entities.impl;

import pl.piotrtrybala.engine.auth.Permissions;
import pl.piotrtrybala.engine.auth.entities.Group;
import pl.piotrtrybala.engine.auth.entities.User;
import pl.piotrtrybala.engine.entities.UserRepository;

import java.sql.*;

public class UserRepositoryImpl implements UserRepository {

    private Connection connection;

    public UserRepositoryImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public User retrieve(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            String username = result.getString("username");
            String password = result.getString("password");
            boolean root = result.getBoolean("root");
            String rawPerms = result.getString("perms");
            Permissions permissions = Permissions.parse(rawPerms);
            Timestamp createdAt = result.getTimestamp("created_at");
            Timestamp updatedAt = result.getTimestamp("updated_at");

            return new User(id, username, password, root, permissions, createdAt, updatedAt);
        } catch (SQLException e) {
            throw new SQLException("failed to retrieve user by id: " + id);
        }
    }

    @Override
    public User retrieve(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();

            int id = result.getInt("id");
            String password = result.getString("password");
            boolean root = result.getBoolean("root");
            String rawPerms = result.getString("perms");
            Permissions permissions = Permissions.parse(rawPerms);
            Timestamp createdAt = result.getTimestamp("created_at");
            Timestamp updatedAt = result.getTimestamp("updated_at");

            return new User(id, username, password, root, permissions, createdAt, updatedAt);
        } catch (SQLException e) {
            throw new SQLException("failed to retrieve user by username: " + username);
        }
    }

    @Override
    public User create(String username, String password, boolean root, Permissions permissions) throws SQLException {
        String sql = "INSERT INTO users(username, password, root, perms) VALUES (?, ?, ?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setBoolean(3, root);
            stmt.setString(4, permissions.save());

            stmt.executeUpdate();

            try (ResultSet inserted = stmt.getGeneratedKeys()) {
                if (inserted.next()) {
                    int id = inserted.getInt("id");
                    Timestamp createdAt = inserted.getTimestamp("created_at");
                    Timestamp updatedAt = inserted.getTimestamp("updated_at");

                    return new User(id, username, password, root, permissions, createdAt, updatedAt);
                } else {
                    throw new SQLException("failed to create user, no id");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("failed to create user: " + e.getMessage());
        }
    }

    @Override
    public void save(User user) throws SQLException {
        this.create(user.getUsername(), user.getPassword(), user.isRoot(), user.getPermissions());
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("failed to delete user: " + e.getMessage());
        }
    }

    @Override
    public void delete(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("failed to delete user: " + e.getMessage());
        }
    }
}
