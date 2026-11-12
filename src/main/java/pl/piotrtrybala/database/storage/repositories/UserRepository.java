package pl.piotrtrybala.database.storage.repositories;

import pl.piotrtrybala.database.auth.permissions.Permissions;
import pl.piotrtrybala.database.storage.types.User;

import java.sql.SQLException;

public interface UserRepository {

    User retrieve(int id) throws SQLException;

    User retrieve(String username) throws SQLException;

    User create(String username, String password, boolean root, Permissions permissions) throws SQLException;

    User save(User user) throws SQLException;

    User update(User user) throws SQLException;

    void delete(int id) throws SQLException;

    void delete(String name) throws SQLException;
}
