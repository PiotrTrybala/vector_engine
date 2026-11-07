package pl.piotrtrybala.database.entities.impl;

import pl.piotrtrybala.database.auth.entities.User;
import pl.piotrtrybala.database.entities.UserGroupRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserGroupRepositoryImpl implements UserGroupRepository {

    private final Connection connection;

    public UserGroupRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int retrieveGroupId(int userId) throws SQLException {

        // one group per user

        String sql = "SELECT gid FROM u_groups WHERE uid = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return result.getInt("gid");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new SQLException("failed to retrieve group id: " + e.getMessage());
        }
    }

    @Override
    public void save(User user) throws SQLException {

        if (user.getGroup() == null) {
            throw new SQLException("group is null");
        }

        int userId = user.getId();
        if (this.retrieveGroupId(userId) != -1) {
            return;
        }

        int groupId = user.getGroup().getId();


        String sql = "INSERT INTO u_groups (uid, gid) VALUES (?, ?);";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, groupId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("failed to add user to group: " + e.getMessage());
        }
    }
}
