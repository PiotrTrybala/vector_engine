package pl.piotrtrybala.database.storage.repositories;

import pl.piotrtrybala.database.storage.types.User;

import java.sql.SQLException;

public interface UserGroupRepository {
    int retrieveGroupId(int userId) throws SQLException;

    void save(User user) throws SQLException;
}
