package pl.piotrtrybala.database.entities;

import pl.piotrtrybala.database.entities.types.Collection;

import java.sql.SQLException;
import java.util.List;

public interface CollectionRepository {

    List<Collection> list(int userId) throws SQLException;

    Collection retrieve(int id) throws SQLException;

    Collection retrieve(int userId, String name) throws SQLException;

    Collection create(int userId, String name) throws SQLException;

    Collection save(Collection collection) throws SQLException;

    Collection update(Collection collection) throws SQLException;

    void delete(int id) throws SQLException;

    void delete(int userId, String name) throws SQLException;
}
