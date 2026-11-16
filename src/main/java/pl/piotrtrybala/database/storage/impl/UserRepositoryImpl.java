package pl.piotrtrybala.database.storage.impl;

import pl.piotrtrybala.database.auth.permissions.Permissions;
import pl.piotrtrybala.database.storage.ConnectionManager;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.storage.repositories.UserRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Optional<User> findById(Integer id) throws RepositoryException {

        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try(ResultSet result = stmt.executeQuery()) {
                if (!result.next()) throw new RepositoryException("could not found user: " + id);
            }

            String username = result.getString("username");
            String password = result.getString("password");
            boolean root = result.getBoolean("root");
            String rawPerms = result.getString("perms");
            Permissions permissions = Permissions.parse(rawPerms);
            Timestamp createdAt = result.getTimestamp("created_at");
            Timestamp updatedAt = result.getTimestamp("updated_at");

            return Optional.of(new User(id, username, password, root, permissions, createdAt, updatedAt));
        } catch (SQLException e) {
            throw new RepositoryException("failed to retrieve user by id: " + id);
        } catch (Exception e) {
            throw new RepositoryException("failed to parse perms: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public <S> List<User> search(S options) throws SQLException {
        return List.of();
    }

    @Override
    public Integer insert(User entity) throws SQLException {
        return 0;
    }

    @Override
    public boolean update(User entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws SQLException {
        return false;
    }

//    private Connection connection;
//
//    public UserRepositoryImpl(Connection connection) {
//        this.connection = connection;
//    }
//
//
//    @Override
//    public User retrieve(int id) throws SQLException {
//        String sql = "SELECT * FROM users WHERE id = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            ResultSet result = stmt.executeQuery();
//
//            String username = result.getString("username");
//            String password = result.getString("password");
//            boolean root = result.getBoolean("root");
//            String rawPerms = result.getString("perms");
//            Permissions permissions = Permissions.parse(rawPerms);
//            Timestamp createdAt = result.getTimestamp("created_at");
//            Timestamp updatedAt = result.getTimestamp("updated_at");
//
//            return new User(id, username, password, root, permissions, createdAt, updatedAt);
//        } catch (SQLException e) {
//            throw new SQLException("failed to retrieve user by id: " + id);
//        } catch (Exception e) {
//            throw new SQLException("failed to parse perms: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public User retrieve(String username) throws SQLException {
//        String sql = "SELECT * FROM users WHERE username = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, username);
//            ResultSet result = stmt.executeQuery();
//
//            int id = result.getInt("id");
//            String password = result.getString("password");
//            boolean root = result.getBoolean("root");
//            String rawPerms = result.getString("perms");
//            Permissions permissions = Permissions.parse(rawPerms);
//            Timestamp createdAt = result.getTimestamp("created_at");
//            Timestamp updatedAt = result.getTimestamp("updated_at");
//
//            return new User(id, username, password, root, permissions, createdAt, updatedAt);
//        } catch (SQLException e) {
//            throw new SQLException("failed to retrieve user by username: " + username);
//        } catch (Exception e) {
//            throw new SQLException("failed to parse perms: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public User create(String username, String password, boolean root, Permissions permissions) throws SQLException {
//        String sql = "INSERT INTO users(username, password, root, perms) VALUES (?, ?, ?, ?) RETURNING id, created_at, updated_at;";
//        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setString(1, username);
//            stmt.setString(2, password);
//            stmt.setBoolean(3, root);
//            stmt.setString(4, permissions.save());
//
//            try (ResultSet inserted = stmt.executeQuery()) {
//                if (inserted.next()) {
//                    int id = inserted.getInt("id");
//                    Timestamp createdAt = inserted.getTimestamp("created_at");
//                    Timestamp updatedAt = inserted.getTimestamp("updated_at");
//
//                    return new User(id, username, password, root, permissions, createdAt, updatedAt);
//                } else {
//                    throw new SQLException("failed to create user, no id");
//                }
//            }
//        } catch (SQLException e) {
//            throw new SQLException("failed to create user: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public User save(User user) throws SQLException {
//        return this.create(user.getUsername(), user.getPassword(), user.isRoot(), user.getPermissions());
//    }
//
//    @Override
//    public User update(User user) throws SQLException {
//        String sql = "UPDATE users SET username = ?, password = ?, root = ?, perms = ? WHERE id = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setString(1, user.getUsername());
//            stmt.setString(2, user.getPassword());
//            stmt.setBoolean(3, user.isRoot());
//            stmt.setString(4, user.getPermissions().save());
//            stmt.setInt(5, user.getId());
//            stmt.executeUpdate();
//
//            return user;
//        } catch (SQLException e) {
//            throw new SQLException("failed to create user: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void delete(int id) throws SQLException {
//        String sql = "DELETE FROM users WHERE id = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new SQLException("failed to delete user: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void delete(String username) throws SQLException {
//        String sql = "DELETE FROM users WHERE username = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, username);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new SQLException("failed to delete user: " + e.getMessage());
//        }
//    }
}
