package pl.piotrtrybala.engine.entities;

import pl.piotrtrybala.engine.auth.entities.User;

import java.sql.SQLException;

public interface UserGroupRepository {

    void save(User user) throws SQLException;

}
