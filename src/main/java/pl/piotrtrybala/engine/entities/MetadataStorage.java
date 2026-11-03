package pl.piotrtrybala.engine.entities;

import pl.piotrtrybala.engine.entities.impl.CollectionRepositoryImpl;

import java.sql.SQLException;

public class MetadataStorage {

    private MetadataStorage instance;

    private CollectionRepository collectionRepository;

    public synchronized MetadataStorage getInstance() {
        try {
            if (instance == null) {
                collectionRepository = new CollectionRepositoryImpl(ConnectionManager.getConnection());
            }
            return instance;
        } catch(SQLException e) {
            throw new RuntimeException("failed to retrieve auth manager: " + e.getMessage());
        }
    }

    public CollectionRepository getCollectionRepository() {
        return collectionRepository;
    }
}
