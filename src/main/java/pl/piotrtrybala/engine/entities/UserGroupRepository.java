package pl.piotrtrybala.engine.entities;

import pl.piotrtrybala.engine.auth.entities.User;

import java.sql.SQLException;

public interface UserGroupRepository {
    int retrieveGroupId(int userId) throws SQLException;

    void save(User user) throws SQLException;
}
