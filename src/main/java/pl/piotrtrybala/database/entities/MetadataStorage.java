package pl.piotrtrybala.database.entities;

import pl.piotrtrybala.database.entities.impl.CollectionRepositoryImpl;

import java.sql.SQLException;

public class MetadataStorage {

    private static MetadataStorage instance;

    private CollectionRepository collectionRepository;

    public static synchronized MetadataStorage getInstance() {
        try {
            if (instance == null) {
                instance = new MetadataStorage();
                instance.collectionRepository = new CollectionRepositoryImpl(ConnectionManager.getConnection());
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
