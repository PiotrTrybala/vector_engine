package pl.piotrtrybala.database.storage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository<T, ID, S> {

    Optional<T> findById(ID id) throws SQLException;

    List<T> findAll() throws SQLException;

    List<T> search(S options) throws SQLException;

    ID insert(T entity) throws SQLException;

    boolean update(T entity) throws SQLException;

    boolean delete(ID id) throws SQLException;

}
