package pl.piotrtrybala.database.storage.impl;

import pl.piotrtrybala.database.auth.permissions.Permissions;
import pl.piotrtrybala.database.storage.ConnectionManager;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Group;
import pl.piotrtrybala.database.storage.repositories.GroupRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupRepositoryImpl implements GroupRepository {

    public GroupRepositoryImpl() {}

    @Override
    public Optional<Group> findById(Integer id) throws RepositoryException {
        String sql = "SELECT * FROM groups WHERE id = ?";

        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) throw new RepositoryException("could not found user: " + id);

                String name = result.getString("name");
                String rawPerms = result.getString("perms");
                Permissions permissions = null;
                try {
                    permissions = Permissions.parse(rawPerms);
                } catch(Exception e) {
                    throw new RepositoryException("failed to parse permissions: " + e.getMessage());
                }
                Timestamp createdAt = result.getTimestamp("created_at");
                Timestamp updatedAt = result.getTimestamp("updated_at");

                return Optional.of(new Group(id, name, permissions, createdAt, updatedAt));
            }

        } catch(SQLException e) {
            throw new RepositoryException("failed to retrieve group: " + id);
        }
    }

    public Optional<Group> findByName(String name) throws RepositoryException {
        String sql = "SELECT * FROM groups WHERE name = ?";

        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {

            stmt.setString(1, name);
            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) throw new RepositoryException("could not found user: " + name);

                int id = result.getInt("id");
                String rawPerms = result.getString("perms");
                Permissions permissions = null;
                try {
                    permissions = Permissions.parse(rawPerms);
                } catch(Exception e) {
                    throw new RepositoryException("failed to parse permissions: " + e.getMessage());
                }
                Timestamp createdAt = result.getTimestamp("created_at");
                Timestamp updatedAt = result.getTimestamp("updated_at");

                return Optional.of(new Group(id, name, permissions, createdAt, updatedAt));
            }

        } catch(SQLException e) {
            throw new RepositoryException("failed to retrieve group: " + name);
        }
    }

    @Override
    public List<Group> findAll() throws RepositoryException {
        String sql = "SELECT * FROM groups;";
        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {
            List<Group> groups = new ArrayList<>();
            try(ResultSet result = stmt.executeQuery()) {
                while(result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String rawPerms = result.getString("perms");
                    Permissions permissions = null;
                    try {
                        permissions = Permissions.parse(rawPerms);
                    } catch(Exception e) {
                        throw new RepositoryException("failed to parse permissions: " + e.getMessage());
                    }
                    Timestamp createdAt = result.getTimestamp("created_at");
                    Timestamp updatedAt = result.getTimestamp("updated_at");

                    groups.add(new Group(id, name, permissions, createdAt, updatedAt));
                }
            }
            return groups;
        } catch (SQLException e) {
            throw new RepositoryException("failed to list all groups: " + e.getMessage());
        }
    }

    @Override
    public Integer insert(Group entity) throws RepositoryException {
        String sql = "INSERT INTO groups(name, perms) VALUES (?, ?) RETURNING id;";
        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getPermissions().save());

            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) throw new RepositoryException("failed to create new group");
                return result.getInt("id");
            }
        } catch(SQLException e) {
            throw new RepositoryException("failed to create new group: " + e.getMessage());
        }
    }

    @Override
    public boolean update(Group entity) throws RepositoryException {
        String sql = "UPDATE groups SET name = ?, perms = ? WHERE id = ?;";
        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getPermissions().save());
            stmt.setInt(3, entity.getId());

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RepositoryException("failed to create group: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Integer id) throws RepositoryException {
        String sql = "DELETE FROM groups WHERE id = ?";
        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)){
            stmt.setInt(1, id);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RepositoryException("failed to delete group: " + e.getSQLState() + ", " + e.getMessage());
        }
    }

    public boolean delete(String name) throws RepositoryException {
        String sql = "DELETE FROM groups WHERE name = ?";
        try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)){
            stmt.setString(1, name);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RepositoryException("failed to create group: " + e.getMessage());
        }
    }
}
