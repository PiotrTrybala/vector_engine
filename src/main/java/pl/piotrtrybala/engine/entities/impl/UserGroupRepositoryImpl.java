package pl.piotrtrybala.engine.entities.impl;

import pl.piotrtrybala.engine.auth.entities.User;
import pl.piotrtrybala.engine.entities.UserGroupRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserGroupRepositoryImpl implements UserGroupRepository {

    private final Connection connection;

    public UserGroupRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(User user) throws SQLException {

        if (user.getGroup() == null) {
            throw new SQLException("group is null");
        }

        int userId = user.getId();
        int groupId = user.getGroup().getId();

        String sql = "INSERT INTO u_groups (uid, gid) VALUES (?, ?);";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, userId);
            stmt.setInt(2, groupId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("failed to add user to group: " + e.getMessage());
        }
    }
}
