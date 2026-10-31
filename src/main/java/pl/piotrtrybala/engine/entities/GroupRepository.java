package pl.piotrtrybala.engine.entities;

import pl.piotrtrybala.engine.auth.Permissions;
import pl.piotrtrybala.engine.auth.entities.Group;

import java.sql.SQLException;

public interface GroupRepository {
    Group retrieve(int id) throws SQLException;
    Group retrieve(String name) throws SQLException;
    Group create(String name, Permissions permissions) throws SQLException;
    void delete(int id) throws SQLException;
    void delete(String name) throws SQLException;
}
