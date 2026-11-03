package pl.piotrtrybala.engine.entities;

import pl.piotrtrybala.engine.entities.types.Collection;

import java.sql.SQLException;

public interface CollectionRepository {

    Collection retrieve(int id) throws SQLException;

    Collection retrieve(int userId, String name) throws SQLException;

    Collection create(int userId, String name) throws SQLException;

    Collection save(Collection collection) throws SQLException;

    Collection update(Collection collection) throws SQLException;

    void delete(int id) throws SQLException;

    void delete(int userId, String name) throws SQLException;
}
