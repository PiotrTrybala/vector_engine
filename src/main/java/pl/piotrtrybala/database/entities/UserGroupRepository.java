package pl.piotrtrybala.database.entities;

import pl.piotrtrybala.database.auth.entities.User;

import java.sql.SQLException;

public interface UserGroupRepository {
    int retrieveGroupId(int userId) throws SQLException;

    void save(User user) throws SQLException;
}
