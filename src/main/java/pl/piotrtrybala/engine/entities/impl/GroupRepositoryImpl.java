package pl.piotrtrybala.engine.entities.impl;

import pl.piotrtrybala.engine.auth.Permissions;
import pl.piotrtrybala.engine.auth.entities.Group;
import pl.piotrtrybala.engine.entities.GroupRepository;

import java.sql.*;

public class GroupRepositoryImpl implements GroupRepository {

    private Connection connection;

    public GroupRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Group retrieve(int id) throws SQLException {
        String sql = "SELECT * FROM groups WHERE id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            if (result.next()){
                String name = result.getString("name");
                String rawPerms = result.getString("perms");
                Permissions permissions = Permissions.parse(rawPerms);
                Timestamp createdAt = result.getTimestamp("created_at");
                Timestamp updatedAt = result.getTimestamp("updated_at");

                return new Group(id, name, permissions, createdAt, updatedAt);
            } else {
                throw new SQLException("no group was found: " + id);
            }
        } catch (SQLException e) {
            throw new SQLException("failed to retrieve group: " + id);
        } catch (Exception e) {
            throw new SQLException("failed to parse perms: " + e.getMessage());
        }
    }

    @Override
    public Group retrieve(String name) throws SQLException {
        String sql = "SELECT * FROM groups WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            ResultSet result = stmt.executeQuery();

            int id = result.getInt("id");
            String rawPerms = result.getString("perms");
            Permissions permissions = Permissions.parse(rawPerms);
            Timestamp createdAt = result.getTimestamp("created_at");
            Timestamp updatedAt = result.getTimestamp("updated_at");

            return new Group(id, name, permissions, createdAt, updatedAt);
        } catch (SQLException e) {
            throw new SQLException("failed to retrieve group: " + name);
        } catch (Exception e) {
            throw new SQLException("failed to parse perms: " + e.getMessage());
        }
    }

    @Override
    public Group create(String name, Permissions permissions) throws SQLException {
        String sql = "INSERT INTO groups(name, perms) VALUES (?, ?) RETURNING id, created_at, updated_at;";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            System.out.println("permissions string: " + permissions.save());
            stmt.setString(1, name);
            stmt.setString(2, permissions.save());

            try (ResultSet inserted = stmt.executeQuery()) {
                if (inserted.next()) {
                    int id = inserted.getInt("id");
                    Timestamp createdAt = inserted.getTimestamp("created_at");
                    Timestamp updatedAt = inserted.getTimestamp("updated_at");

                    return new Group(id, name, permissions, createdAt, updatedAt);
                } else {
                    throw new SQLException("failed to create group, no id");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("failed to create group: " + e.getMessage());
        }
    }

    @Override
    public Group save(Group group) throws SQLException {
        return this.create(group.getName(), group.getPermissions());
    }

    @Override
    public Group update(Group group) throws SQLException {
        String sql = "UPDATE groups SET name = ?, perms = ? WHERE id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, group.getName());
            stmt.setString(2, group.getPermissions().save());
            stmt.setInt(3, group.getId());

            stmt.executeUpdate();
            return group;
        } catch (SQLException e) {
            throw new SQLException("failed to create group: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM groups WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("failed to create group: " + e.getSQLState() + ", " + e.getMessage());
        }
    }

    @Override
    public void delete(String name) throws SQLException {
        String sql = "DELETE FROM groups WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("failed to create group: " + e.getMessage());
        }
    }
}
