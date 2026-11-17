package pl.piotrtrybala.database.storage.impl;

import pl.piotrtrybala.database.storage.ConnectionManager;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.repositories.GroupRepository;
import pl.piotrtrybala.database.storage.repositories.UserRepository;
import pl.piotrtrybala.database.storage.types.Group;
import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.storage.repositories.UserGroupRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserGroupRepositoryImpl implements UserGroupRepository {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public UserGroupRepositoryImpl(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Optional<User> getUserByGroupId(int groupId) throws RepositoryException {
        String sql = "SELECT gid FROM u_groups WHERE gid = ?;";
        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, groupId);
            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) return Optional.empty();

                int userId = result.getInt("uid");
                return userRepository.findById(userId);
            }
        } catch (SQLException e) {
            throw new RepositoryException("failed to retrieve group id: " + e.getMessage());
        }
    }

    @Override
    public Optional<Group> getGroupByUserId(int userId) throws RepositoryException {
        String sql = "SELECT gid FROM u_groups WHERE uid = ?;";
        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) return Optional.empty();

                int groupId = result.getInt("gid");
                return groupRepository.findById(groupId);
            }
        } catch (SQLException e) {
            throw new RepositoryException("failed to retrieve group id: " + e.getMessage());
        }
    }

    @Override
    public boolean insertUserGroup(User user) throws RepositoryException {

        if (user.getGroup() == null) {
            throw new RepositoryException("group is not set for this user");
        }

        int userId = user.getId();
        Optional<Group> group = this.getGroupByUserId(userId);
        if (group.isEmpty()) {
            throw new RepositoryException("user is already in this group");
        }

        String sql = "INSERT INTO u_groups(uid, gid) VALUES (?, ?);";
        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, group.get().getId());
            return stmt.executeUpdate() == 1;
        } catch(SQLException e) {
            throw new RepositoryException("failed to assign user to group: " + e.getMessage());
        }
    }
}
