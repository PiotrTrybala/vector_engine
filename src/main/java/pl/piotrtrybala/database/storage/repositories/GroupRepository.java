package pl.piotrtrybala.database.storage.repositories;

import pl.piotrtrybala.database.auth.permissions.Permissions;
import pl.piotrtrybala.database.storage.types.Group;

import java.sql.SQLException;

public interface GroupRepository {
    Group retrieve(int id) throws SQLException;

    Group retrieve(String name) throws SQLException;

    Group create(String name, Permissions permissions) throws SQLException;

    Group save(Group group) throws SQLException;

    Group update(Group group) throws SQLException;

    void delete(int id) throws SQLException;

    void delete(String name) throws SQLException;
}
