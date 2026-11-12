package pl.piotrtrybala.database.storage;

import java.sql.SQLException;

public class RepositoryException extends Exception {
    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(SQLException e) {
        super(e.getMessage()); // add better handling
    }
}
