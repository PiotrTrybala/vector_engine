package pl.piotrtrybala.database.entities;

import pl.piotrtrybala.database.entities.impl.CollectionRepositoryImpl;
import pl.piotrtrybala.database.entities.impl.EmbeddingRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class MetadataStorage {

    private static MetadataStorage instance;

    private CollectionRepository collectionRepository;
    private EmbeddingRepository embeddingRepository;

    public static synchronized MetadataStorage getInstance() {
        try {
            if (instance == null) {
                instance = new MetadataStorage();
                instance.collectionRepository = new CollectionRepositoryImpl(ConnectionManager.getConnection());
                instance.embeddingRepository = new EmbeddingRepositoryImpl(ConnectionManager.getConnection());
            }
            return instance;
        } catch(SQLException e) {
            throw new RuntimeException("failed to retrieve auth manager: " + e.getMessage());
        }
    }

    public CollectionRepository getCollectionRepository() {
        return collectionRepository;
    }

    public EmbeddingRepository getEmbeddingRepository() {
        return embeddingRepository;
    }
}
