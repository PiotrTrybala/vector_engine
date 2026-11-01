package pl.piotrtrybala.engine.entities;

import pl.piotrtrybala.engine.auth.Permissions;
import pl.piotrtrybala.engine.auth.entities.User;

import java.sql.SQLException;

public interface UserRepository {

    User retrieve(int id) throws SQLException;

    User retrieve(String username) throws SQLException;

    User create(String username, String password, boolean root, Permissions permissions) throws SQLException;

    void save(User user) throws SQLException;

    void delete(int id) throws SQLException;

    void delete(String name) throws SQLException;
}
